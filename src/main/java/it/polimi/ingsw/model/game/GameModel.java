package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.LobbyInfoMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel extends Observable implements Serializable {
    private static final long serialVersionUID = -8113521898963437960L;
    private List<IslandModel> islandModels;
    private int playersNumber=0;
    private List<PlayerModel> playersModels = new ArrayList<>();
    private List<CloudModel> cloudsModel;
    private List<ColorPawns> bag;
    public GameMode mode;
    private List<AssistantCardModel> deck = null;
    private List<AssistantCardModel> cemetery = new ArrayList<>();
    private List<PlayerModel> phaseOrder;
<<<<<<< HEAD
=======

>>>>>>> main
    private boolean havePlayerFinishedCards = false;
    public static final String SERVER_NICKNAME = "server";
    private static GameModel instance = new GameModel();

    /**
     *
     * @return The list of the player in game or an empty list if there aren't players
     * @throws NullPointerException If playersModel is null
     */
    public List<PlayerModel> getPlayersModel() throws NullPointerException{
        /*try{
            return playersModels;
        }catch (NullPointerException e){
            return new ArrayList<>();
        }*/
        return playersModels;
    }

<<<<<<< HEAD
=======

>>>>>>> main
    /**
     *
     * @param playersNumber The number of the players joined the current match
     */
    public void setPlayerNumber(int playersNumber){
        this.playersNumber = playersNumber;
    }

<<<<<<< HEAD
=======
    //da testare

>>>>>>> main
    /**
     *
     * @param player A player that has to be added to the current match
     */
    public void addPlayer(PlayerModel player){
        this.playersModels.add(player);
    }

    /**
     *
     * @param nickname The nickname of the player that has to be removed from the current match
     * @return The outcome of the removal
     */
    public boolean removePlayerByNickname(String nickname) {
        PlayerModel playerToRemove = getPlayerByNickname(nickname);
        boolean result = playersModels.remove(playerToRemove);
<<<<<<< HEAD
        notifyObserver(new LobbyInfoMessage(playersModels));
=======

        notifyObserver(new LobbyInfoMessage(playersModels));


>>>>>>> main
        return result;
    }

    /**
     * It sets the istance to null
     */
    public static void resetInstance() {
        GameModel.instance = null;
    }
    //singleton pattern: quando dovrò usare Game in un'altra classe dovrò fare:
    //Game g = Game.getInstance();
    //e usare g come oggetto normale

    /**
     * Creates a new instance of GameModel if there isn't one already
     * @return The instance of the current game
     */
    public static synchronized GameModel getInstance(){
        if (instance == null)
            instance = new GameModel();
        return instance;
    }

    /**
     * Sets the list of players and its size for the current game
     * @param playersModels The list of players
     */
    public void setPlayers(List<PlayerModel> playersModels){
        this.playersModels = playersModels; //lista dei giocatori
        this.playersNumber = playersModels.size();
        this.cemetery = new ArrayList<>(playersNumber);
        this.phaseOrder = playersModels; //quando non hanno ancora giocato la carta l'ordine è quello in della lista dei player
    }

    /**
     *
     * @param phaseOrder The new order phase calculated through the assistant cards played
     */
    public void setPhaseOrder(List<PlayerModel> phaseOrder){
        this.phaseOrder = phaseOrder;
    }

    /**
     *
     * @return The order phase of the current round
     */
    public List<PlayerModel> getPhaseOrder(){
        return this.phaseOrder;
    }

    /**
     * It resets the order phase for the next turn
     */
    public void clearPhaseOrder(){
        this.phaseOrder = new ArrayList<>(playersNumber);
    }

    /**
     *
     * @param studentsBag The list of students to store in the bag
     */
    public void setBag(List<ColorPawns> studentsBag){
        this.bag = studentsBag;
    }

    /**
     *
     * @param assistantCardModel The assistant card to add to the central deck
     */
    public void addCardToDeck(AssistantCardModel assistantCardModel){
        if(deck == null) deck = new ArrayList<>(40);
        this.deck.add(assistantCardModel);
    }

    /**
     *
     * @return The number of players in game
     */
    public int getPlayersNumber(){
        return this.playersNumber;
    }

    /**
     *
     * @return The list of assistant card of the central deck of the game
     */
    public List<AssistantCardModel> getDeck() {
        return deck;
    }

    /**
     *
     * @param nickname The name of the player to find
     * @return The player who matches the given nickname
     */
    public PlayerModel getPlayerByNickname(String nickname){
        return playersModels.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findAny().get();
    }

    /**
     *
     * @param islandsModel The island to add to the game
     */
    public void setIslands(List<IslandModel> islandsModel) {
        this.islandModels = islandsModel;
    }
    /**
     *
     * @return The list of the island of the game
     */
    public List<IslandModel> getIslandsModel(){
        return this.islandModels;
    }

    /**
     *
     * @return The list of the clouds of the game
     */
    public List<CloudModel> getCloudsModel() {
        return cloudsModel;
    }

    /**
     *
     * @param cloudsModel The list of clouds to add to the game
     */
    public void setCloudsModel(List<CloudModel> cloudsModel) {
        this.cloudsModel = cloudsModel;
    }

    /**
     *
     * @param mode The game mode chosen between beginner and expert
     */
    public void setGameMode(GameMode mode){
        this.mode = mode;
    }

    /**
     *
     * @return The game mode of the current game
     */
    public GameMode getGameMode(){
        return mode;
    }

    /**
     *
     * @return The list of students in the bag
     */
    public List<ColorPawns> getBag() {
        return bag;
    }

    /**
     *
     * @return The list of the assistant cards in the cemetery
     */
    public List<AssistantCardModel> getCemetery() {
        return cemetery;
    }

    /**
     *
     * @param card The played card that has to be put in the cemetery
     */
    public void addToCemetery(AssistantCardModel card) {
        this.cemetery.add(card);
    }

    /**
     *
     * @param colorTower The tower color of which you want to find the player
     * @return The player who has the given tower color
     */
    public PlayerModel getPlayerByColorTower(ColorTower colorTower){
        for(PlayerModel p : this.playersModels) {
            if(p.getColorTower().equals(colorTower) && p.getTowerNumber() != 0) return p;
        }
        return new PlayerModel();
    }

    /**
     *
     * @param nickname The nickname chosen by the player and that has to be verified
     * @return True if the nickname chosen is already taken by another player
     */
    public boolean isNicknameTaken(String nickname) {
        return playersModels.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }

    /**
     * It ends the game. It' used in case of error and in tests
     */
    public void endGame() {
        GameModel.resetInstance();

        Server.LOGGER.info("Game finished. ");
    }


    //convenzione: senso orario
    /**
     * Explores the adjacent islands and checks if there are others with the same tower color
     * @return The direction where we can find an island with the same influence
     */
    public ColorDirectionAdjacentIsland getAdjacentSameColor(IslandModel islandModelToCheck){
        if(islandModelToCheck.getTowerColor() == ColorTower.NULL){
            return ColorDirectionAdjacentIsland.NONE;
        }
        //indice dell'isola nell'array delle isole
        int right, left;
        int indexIslandToCheckAdjacent = this.islandModels.indexOf(islandModelToCheck);
        if (indexIslandToCheckAdjacent != this.islandModels.size()-1)
            right = indexIslandToCheckAdjacent + 1;
        else
            right = 0;
        if (indexIslandToCheckAdjacent != 0)
            left = indexIslandToCheckAdjacent - 1;
        else
            left = this.islandModels.size()-1;
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

    /**
     * Checks which player is the winner. It depends on the number of tower placed on island or in case of draw on the number of prof owned. It's called when there are only 3 islands, the last card is played or the last student is taken from the bag
     * @return The tower color of the winning player
     */
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
        List<Integer> indexesPlayersSameTowerNum = new ArrayList<>();
        int i; // is the index Of Player With Same Tower Number (if any)
        for (i = 0; i < playersModels.size(); i++) {
            if (i!=indexMinTower && playersModels.get(i).getTowerNumber() == minNumTower) {
                count++; // ci sono giocatori con stesso numero minimo di torri
                indexesPlayersSameTowerNum.add(i);
            }
        }
        if (count != 0) { //se ci sono due giocatori con stesso # di torri -> controlla numProf
            //prende, tra i quelli a parità di torri, il giocatore con più prof
            int profMax = playersModels.get(indexesPlayersSameTowerNum.get(0)).getNumProfs();
            PlayerModel candidatePlayer = playersModels.get(indexesPlayersSameTowerNum.get(0));
            for(int j = 1; j < indexesPlayersSameTowerNum.size(); j++){
                if(playersModels.get(indexesPlayersSameTowerNum.get(j)).getNumProfs() > profMax){
                    profMax = playersModels.get(indexesPlayersSameTowerNum.get(j)).getNumProfs();
                    candidatePlayer = playersModels.get(indexesPlayersSameTowerNum.get(j));
                }
            }
            return candidatePlayer.getColorTower();
        } else {        //controlla torri -> restituisce quello con minimo numero di torri
            return playersModels.get(towersNumber.indexOf(minNumTower)).getColorTower();

        }
    }


    /**
     *
     * @return The island where mother nature is placed
     */
    public IslandModel getIslandWithMother(){
        return this.islandModels.stream().filter(IslandModel::getMotherNature).findAny().orElse(null);
    }

    /**
     *
     * @return The index of the island where mother nature in placed
     */
    public int getMotherNatureIndex(){
        int index = 0;
        for(; !this.islandModels.get(index).getMotherNature(); index++);
        return index;
    }

    /**
     * It's used in checkedWin
     * @return True if a player has finished his cards
     */
    public boolean havePlayersFinishedCards() {
        return havePlayerFinishedCards;
    }

    /**
     * It set havePlayerFinishedCards to true if a player has finished his assistant cards
     */
    public void setTrueHavePlayerFinishedCards(){
        this.havePlayerFinishedCards = true;
    }
}
