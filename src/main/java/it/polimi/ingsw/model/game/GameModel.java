package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.ChacterCardModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameModel {
    private List<IslandModel> islandModels;

    private int playersNumber;
    private List<PlayerModel> playersModels;
    private List<CloudModel> cloudsModel;
    private List<ColorPawns> bag; //non sicuro sui 130
    private PhaseGame gameState;
    public GameMode mode;
    public List<ChacterCardModel> chosenCards;
    private List<AssistantCardModel> deck;
    private static GameModel istance = new GameModel();

    public List<PlayerModel> getPlayersModel() throws NullPointerException{
        try{
            return playersModels;
        }catch (NullPointerException e){
            return new ArrayList<>();
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

    public void setPlayers(List<PlayerModel> playersModels){
        this.playersModels = playersModels; //lista dei giocatori
        this.playersNumber = playersModels.size();
    }

    public void setBag(List<ColorPawns> studentsBag){
        this.bag = studentsBag;
    }

    public void addCardToDeck(int index, AssistantCardModel assistantCardModel){
        if(deck == null) deck = new ArrayList<>(40);
        this.deck.set(index, assistantCardModel);
    }

    public int getPlayersNumber(){
        return this.playersNumber;
    }
    public List<AssistantCardModel> getDeck() {
        return deck;
    }

    public void setDeck(List<AssistantCardModel> deck) {
        this.deck = deck;
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
        return cloudsModel;
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
}
