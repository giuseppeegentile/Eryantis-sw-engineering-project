package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.ChacterCardModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.List;

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
    private static GameModel instance = new GameModel();

    /**
     *
     * @return The list of the player in game or an empty list if there aren't players
     * @throws NullPointerException
     */
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
    public String getPlayerByNickname(String nickname){
        return playersModels.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findAny().get().getNickname();
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
     * @return The current phase of the game
     */
    public PhaseGame getGameState() {
        return gameState;
    }

    /**
     *
     * @param gameState The next phase of the game
     */
    public void setGameState(PhaseGame gameState) {
        this.gameState = gameState;
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
     * Clears the cemetery replacing the existing one with an empty list
     */
    public void clearCemetery(){
        this.cemetery = new ArrayList<>(playersNumber);
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

}
