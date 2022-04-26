package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.ChacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameModel {
    private List<IslandModel> islandModels;

    private int playersNumber;
    private List<PlayerModel> playersModels;
    private List<CloudModel> cloudsModel;
    private List<ColorPawns> bag; //non sicuro sui 130
    private PhaseGame gameState;
    public GameMode mode;
    public List<ChacterCardModel> chosenCards;
    private List<AssistantCardModel> deck = null;
    private List<AssistantCardModel> cemetery;
    private List<PlayerModel> phaseOrder; //ordine della fase di azione

    private transient Map<String, VirtualView> virtualViewMap;


    public static final String SERVER_NICKNAME = "server";

    private static GameModel istance = new GameModel();

    public List<PlayerModel> getPlayersModel() throws NullPointerException{
        try{
            return playersModels;
        }catch (NullPointerException e){
            return new ArrayList<>();
        }
    }
    public static void resetInstance() {
        GameModel.istance = null;
    }
    //singleton pattern: quando dovrò usare Game in un'altra classe dovrò fare:
    //Game g = Game.getInstance();
    //e usare g come oggetto normale
    public static synchronized GameModel getInstance(){
        if (istance == null)
            istance = new GameModel();
        return istance;
    }

    public void setPlayers(List<PlayerModel> playersModels){
        this.playersModels = playersModels; //lista dei giocatori
        this.playersNumber = playersModels.size();
        this.cemetery = new ArrayList<>(playersNumber);
        this.phaseOrder = playersModels; //quando non hanno ancora giocato la carta l'ordine è quello in della lista dei player
    }

    public void setPhaseOrder(List<PlayerModel> phaseOrder){
        this.phaseOrder = phaseOrder;
    }

    public List<PlayerModel> getPhaseOrder(){
        return this.phaseOrder;
    }

    public void clearPhaseOrder(){
        this.phaseOrder.clear();
        this.phaseOrder = new ArrayList<>(playersNumber);
    }

    public void setBag(List<ColorPawns> studentsBag){
        this.bag = studentsBag;
    }

    public void addCardToDeck(AssistantCardModel assistantCardModel){
        if(deck == null) deck = new ArrayList<>(40);
        this.deck.add(assistantCardModel);
    }

    public int getPlayersNumber(){
        return this.playersNumber;
    }

    public List<AssistantCardModel> getDeck() {
        return deck;
    }

    public String getPlayerByNickname(String nickname){
        return playersModels.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findAny().get().getNickname();
    }

    public void setIslands(List<IslandModel> islandsModel) {
        this.islandModels = islandsModel;
    }

    public List<IslandModel> getIslandsModel(){
        return this.islandModels;
    }


    public List<CloudModel> getCloudsModel() {
        return this.cloudsModel;
    }

    public void setCloudsModel(List<CloudModel> cloudsModel) {
        this.cloudsModel = cloudsModel;
    }

    public void setGameMode(GameMode mode){
        this.mode = mode;
    }

    public GameMode getGameMode(GameMode mode){
        return mode;
    }

    public PhaseGame getGameState() {
        return gameState;
    }

    public void setGameState(PhaseGame gameState) {
        this.gameState = gameState;
    }

    public List<ColorPawns> getBag() {
        return bag;
    }

    public List<AssistantCardModel> getCemetery() {
        return cemetery;
    }

    public void addToCemetery(AssistantCardModel card) {
        this.cemetery.add(card);
    }

    public void clearCemetery(){
        this.cemetery = new ArrayList<>(playersNumber);
    }

    public PlayerModel getPlayerByColorTower(ColorTower colorTower){
        for(PlayerModel p : this.playersModels) {
            if(p.getColorTower().equals(colorTower) && p.getTowerNumber() != 0) return p;
        }
        return new PlayerModel();
    }
    public boolean isNicknameTaken(String nickname) {
        return playersModels.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }
    public void endGame() {
        GameModel.resetInstance();

        Server.LOGGER.info("Game finished. ");
    }

    public void broadcastDisconnectionMessage(String nicknameDisconnected, String text) {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showDisconnectionMessage(nicknameDisconnected, text);
        }
    }

    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }


    public Map<String, VirtualView> getVirtualViewMap() {
        return this.virtualViewMap;
    }

    //convenzione: senso orario
    public ColorDirectionAdjacentIsland getAdjacentSameColor(IslandModel islandModelToCheck){
        //indice dell'isola nell'array delle isole
        int right, left;
        int indexIslandToChekAdjacent = this.islandModels.indexOf(islandModelToCheck);
        if (indexIslandToChekAdjacent != 11)
            right = indexIslandToChekAdjacent + 1;
        else
            right = 0;
        if (indexIslandToChekAdjacent != 0)
            left = indexIslandToChekAdjacent - 1;
        else
            left = 11;
        if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor() && islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor())
            return ColorDirectionAdjacentIsland.BOTH;
        else if(islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor())
            return ColorDirectionAdjacentIsland.RIGHT;
        else if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor())
            return ColorDirectionAdjacentIsland.LEFT;
        else
            return ColorDirectionAdjacentIsland.NONE;
    }

    //da chiamare quando rimangono solo 3 isole unificate
    //alla fine del round in cui viene pescato l'ultimo studente o giocata l'ultima carta
    public ColorTower checkWin() {
        List<PlayerModel> playersModels = this.getPlayersModel();
        //corrispondenza indice(studente) - valore (numero torre)
        List<Integer> towersNumber = new ArrayList<>(playersModels.size());
        //corrispondenza indice(studente) - valore (numero prof)
        List<Integer> profNumber = new ArrayList<>(playersModels.size());
        playersModels.forEach(p -> {
            if(p.getTowerNumber() != 0)
                towersNumber.add(p.getTowerNumber());
            profNumber.add(p.getNumProfs());
        });
        byte count = 0; //numero di giocatori con stesso numero di torri
        int minNumTower = Collections.min(towersNumber);
        int indexMinTower;
        indexMinTower = towersNumber.indexOf(minNumTower);
        int i = 0; // is the index Of Player With Same Tower Number (if any)
        for (i = 0; i < playersModels.size(); i++) {
            if (i!=indexMinTower && playersModels.get(i).getTowerNumber() == minNumTower) {
                count++;
                break; //esci dal ciclo, ci sono due giocatori con stesso numero di torri
            }
        }
        if (count != 0) { //se ci sono giocatori con stesso # di torri -> controlla numProf
            //prende, tra i due a parità di torri, il giocatore con più prof
            return playersModels.get(i).getNumProfs() > playersModels.get(indexMinTower).getNumProfs() ?
                    playersModels.get(i).getColorTower() : playersModels.get(indexMinTower).getColorTower();
        } else {        //controlla torri -> restituisce quello con minimo numero di torri
            return playersModels.get(towersNumber.indexOf(minNumTower)).getColorTower();

        }
    }
}
