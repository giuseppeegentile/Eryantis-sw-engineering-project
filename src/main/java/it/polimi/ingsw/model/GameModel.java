package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel {
    private List<IslandModel> islandModels;

    private int playersNumber;
    private List<PlayerModel> playersModels = new ArrayList<>(playersNumber);
    private List<CloudModel> cloudsModel;
    private List<ColorPawns> bag = new ArrayList<>(130); //non sicuro sui 130
    private StateGame gameState;
    public GameMode mode;
    public List<ChacterCardModel> chosenCards;
    List<AssistantCardModel> deck;
    private static GameModel istance = new GameModel();

    public List<PlayerModel> getPlayersModel() throws NullPointerException{
        try{
            return playersModels;
        }catch (NullPointerException e){
            return new ArrayList<PlayerModel>();
        }
    }

    //singleton pattern: quando dovrò usare Game in un'altra classe dovrò fare:
    //Game g = Game.getInstance();
    //e usare g come oggetto normale
    public static synchronized GameModel getInstance(){
        if (istance == null)
            istance = new GameModel();
        return istance;
    }


    //inizializza il Game, non posso fare il costruttore con parametri, altrimenti non avrebbe senso il singleton
    public void init(List<PlayerModel> playersModels, List<CloudModel> cloudModels, List<ColorPawns> bag, GameMode mode){
        this.playersModels = playersModels; //lista dei giocatori
        this.playersNumber = playersModels.size();
        this.cloudsModel = cloudModels;
        this.bag = bag;

        this.mode = mode;
        gameState = StateGame.ADD_STUDENT_CLOUD;
        //nel controller dovrò capire se attribuire chosenCards, in base alla difficoltà di gioco
    }

    public void setBag(List<ColorPawns> studentsBag){
        this.bag = studentsBag;
    }
    private String getPlayerByNickname(String nickname){
        return playersModels.stream()
                .filter(playerModel -> playerModel.getNickname().equals(nickname))
                .findAny().get().getNickname();
    }

    //da chiamare quando rimangono solo 3 isole unificate
    //alla fine del round in cui viene pescato l'ultimo studente o giocata l'ultima carta
    private PlayerModel checkWin() {
        PlayerModel winner = null;
        //corrispondenza indice(studente) - valore (numero torre)
        List<Integer> towersNumber = new ArrayList<>(playersModels.size());
        //corrispondenza indice(studente) - valore (numero prof)
        List<Integer> profNumber = new ArrayList<>(playersModels.size());
        playersModels.forEach(p -> {
            towersNumber.add(p.getTowerNumber());
            profNumber.add(p.getNumProfs());
        });
        byte count = 0; //numero di giocatori con stesso numero di torri
        int minNumTower, maxNumProf;
        int indexMinTower;
        minNumTower = Collections.min(towersNumber);
        indexMinTower = towersNumber.indexOf(minNumTower);
        int i = 0; // is the index Of Player With Same Tower Number (if any)
        for (i = 0; i < playersModels.size(); i++) {
            if (i!=indexMinTower && playersModels.get(i).getTowerNumber() == minNumTower) {
                count++;
                break; //esci dal ciclo, ci sono due giocatori con stesso numero di torri
            }
        }
        if (count != 0) { //se ci sono giocatori con stesso # di torri -> controlla numProf
            //caso in cui non giocatori hanno stesso numero di torri, e stesso numero di prof
            /*if (playersModels.get(i).getNumProfs() == playersModels.get(indexMinTower).getNumProfs()){
                return new PlayerModel("_NO_WINNER_CASE_",ColorTower.GREY,0);
            }*/
            //prende, tra i due a parità di torri, il giocatore con più prof
            return playersModels.get(i).getNumProfs() > playersModels.get(indexMinTower).getNumProfs() ?
                    playersModels.get(i) : playersModels.get(indexMinTower);
        } else {        //controlla torri -> restituisce quello con minimo numero di torri
            return playersModels.get(towersNumber.indexOf(minNumTower));

        }
    }

    public void setIslands(List<IslandModel> islandsModel) {
        this.islandModels = islandsModel;
    }

    public ColorDirectionAdjacentIsland getAdjacentSameColor(int indexIslandToChekAdjacent){
        if(indexIslandToChekAdjacent == 0 && islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(11).getTowerColor()){
            if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent + 1).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.LEFT;
        }
        if(indexIslandToChekAdjacent == 11 && islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(0).getTowerColor()){
            if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent - 1).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent + 1).getTowerColor()){
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent - 1).getTowerColor()){
            return ColorDirectionAdjacentIsland.LEFT;
        }
        return ColorDirectionAdjacentIsland.NONE;
    }

    public List<IslandModel> getIslandsModel(){
        return this.islandModels;
    }

    private void generateDeck(){
        byte j = 1;
        for(int i = 0; i < 10; i++ ){
            deck.set(i, new AssistantCardModel(i+1, j));
            if(i%2 == 1) j++;
        }
    }
}
