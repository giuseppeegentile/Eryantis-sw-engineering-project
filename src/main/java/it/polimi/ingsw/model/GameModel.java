package it.polimi.ingsw.model;

import java.util.ArrayList;
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
    private static GameModel istance = new GameModel();

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
    private String getPlayerByNickname(String nickname){
        return playersModels.stream()
                .filter(playerModel -> playerModel.getNickname().equals(nickname))
                .findAny().get().getNickname();
    }

    private PlayerModel checkWin(){
        return new PlayerModel("", ColorTower.GREY, 0);
    }

    public void setIslands(List<IslandModel> islandsModel) {
        this.islandModels = islandsModel;
    }

    public List<IslandModel> getIslandsModel(){
        return this.islandModels;
    }
}
