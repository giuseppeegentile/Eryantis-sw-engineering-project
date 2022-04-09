package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.ChacterCardModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel {
    private List<IslandModel> islandModels;

    private int playersNumber;
    private List<PlayerModel> playersModels = new ArrayList<>(playersNumber);
    private List<CloudModel> cloudsModel;
    private List<ColorPawns> bag = new ArrayList<>(130); //non sicuro sui 130
    private PhaseGame gameState;
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
        gameState = PhaseGame.ADD_STUDENT_CLOUD;
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

    public void setIslands(List<IslandModel> islandsModel) {
        this.islandModels = islandsModel;
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
