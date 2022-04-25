package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.*;

import static it.polimi.ingsw.model.ColorPawns.*;

public class PlayerModel {
    private String nickname;
    private Set<ColorPawns> profs; //i prof sono univoci e non possono essere ripetuti, conviene Set
    private int numProfs;
    private int towerNumber; //da settare con il controller, strategy
    private ColorTower colorTower;
    private List<AssistantCardModel> deckAssistantCardModel;
    private Map<ColorPawns, Integer> studentInHall;
    private List<ColorPawns> studentInEntrance;
    private StatePlayer state;
    private byte movementMotherNatureCurrentActionPhase;
    private int coins;


    /**
     * Costructor for player in the model: initialize coins, numProf and mother nature movement to zero. Also set the map for the student hall
     * @param nickname nickname choose by player
     * @param colorTower tower color assigned to a player, identifier for the team in 4-player version
     */
    public PlayerModel(String nickname, ColorTower colorTower){
        this.nickname = nickname;
        this.colorTower = colorTower;
        this.coins = 0;
        this.numProfs = 0;
        this.movementMotherNatureCurrentActionPhase = 0;
        this.studentInHall = Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        );
    }

    /**
     * Costructor for player in the model: initialize coins, numProf and mother nature movement to zero. Also set the map for the student hall
     * @param nickname nickname choose by player
     */
    public PlayerModel(String nickname){
        this.nickname = nickname;
        this.coins = 0;
        this.numProfs = 0;
        this.movementMotherNatureCurrentActionPhase = 0;
        this.studentInHall = Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        );
    }

    /**
     * Costructor for player in the model: initialize colorTower to the null value
     */
    public PlayerModel(){
        this.colorTower = ColorTower.NULL;
    }

    /**
     *
     * @return the number of prof owned by the player
     */
    public int getNumProfs(){
        return this.numProfs;
    }

    /**
     *
     * @param prof prof to remove from the list of profs owned
     */
    public void removeProf(ColorPawns prof){
        this.profs.remove(prof);
        this.numProfs -= 1;
    }

    /**
     *
     * @return the set of profs owned by the player
     */
    public Set<ColorPawns> getProfs(){
        return this.profs;
    }

    /**
     *
     * @param profToAdd prof to add to the set of prof owned
     */
    public void addProf(ColorPawns profToAdd){
        if(this.profs == null)
            this.profs = new HashSet<>();
        this.profs.add(profToAdd);
        this.numProfs += 1;
    }

    /**
     *
     * @return the nickname choose by the player
     */
    public String getNickname(){
        return this.nickname;
    }

    /**
     * Checks if the player has a certain prof
     * @param prof Prof to be checked
     * @return The true value only if the player has the given prof
     */
    public boolean hasProf(ColorPawns prof){
        return this.getProfs().contains(prof);
    }

    /**
     *
     * @param nickname nickname that the player wants
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     *
     * @return the state of the player, which is an enum
     */
    private StatePlayer getState(){
        return this.state;
    }

    /**
     *
     * @param studentToRemove the color of the student to remove from the entrance
     */
    public void removeStudentFromEntrance(ColorPawns studentToRemove){
        int i;
        for(i = this.studentInEntrance.size(); this.studentInEntrance.get(i).equals(studentToRemove) || i >= 0 ; i--);
        this.studentInEntrance.remove(i);
    }

    /**
     * Chooses the assistant card used by the player and removes it from the player's deck
     */
    private static void playCard(){ }

    /**
     *
     * @return The number of coins owned by the player
     */
    private int getCoins(){
        return this.coins;
    }

    /**
     *
     * @param coinsUsed coins used by some cards effects, or other game functionalities. Decrement the coins value
     */
    public void removeCoins(int coinsUsed){
        this.coins -=coinsUsed;
    }

    /**
     * Add one coins to the coins value
     */
    public void addCoins(){
        this.coins +=1 ;
    }

    /**
     * Set coins value to one
     */
    public void setCoins(){
        this.coins = 1;
    }

    /**
     *
     * @param studentInHall Set the Map studentInHall with as key the color of the student, and for value the number of the prof of that color (key) .
     */
    public void setStudentHall(Map<ColorPawns, Integer> studentInHall){
        this.studentInHall = studentInHall;
    }


    /**
     *
     * @return The map of the student in the hall as explained in the set method.
     */
    public Map<ColorPawns, Integer> getStudentInHall(){
        return this.studentInHall;
    }

    /**
     *
     * @return the number of towers in the player board
     */
    public int getTowerNumber(){
        return this.towerNumber;
    }

    /**
     *
     * @param colorTower The color of the tower chosen by the player
     * @param towerNumber The number of tower in the board, which depends on the number of player
     */
    public void setTowers(ColorTower colorTower, int towerNumber){
        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }

    /**
     *
     * @return The movement of mother nature in the current action phase. It depends on the card played.
     */
    public byte getMovementMotherNatureCurrentActionPhase() {
        return movementMotherNatureCurrentActionPhase;
    }

    /**
     *
     * @param movementMotherNatureCurrentActionPhase The movement of mother nature in the current action phase. It depends on the card played.
     */
    public void setMovementMotherNatureCurrentActionPhase(byte movementMotherNatureCurrentActionPhase) {
        this.movementMotherNatureCurrentActionPhase = movementMotherNatureCurrentActionPhase;
    }

    /**
     *
     * @return The deck of assistant cards of the player
     */
    public List<AssistantCardModel> getDeckAssistantCardModel() {
        return deckAssistantCardModel;
    }

    /**
     *
     * @param deckAssistantCardModel The deck of assistant cards to be assigned to the player
     */
    public void setDeckAssistantCardModel(List<AssistantCardModel> deckAssistantCardModel) {
        this.deckAssistantCardModel = deckAssistantCardModel;
    }

    /**
     *
     * @return The tower color of the player
     */
    public ColorTower getColorTower() {
        return colorTower;
    }

    /**
     *
     * @param colorTower The tower color chosen for the player
     */
    public void setTowers(ColorTower colorTower) {
        this.colorTower = colorTower;
    }

    /**
     *
     * @return The list of students placed in the entrance of the player board
     */
    public List<ColorPawns> getStudentInEntrance() {
        return studentInEntrance;
    }

    /**
     *
     * @param studentInEntrance The list of students that has to be placed in the entrance of the player board
     */
    public void setStudentInEntrance(List<ColorPawns> studentInEntrance) {
        this.studentInEntrance = studentInEntrance;
    }

    /**
     *
     * @param state The next state of the player's turn
     */
    public void setState(StatePlayer state) {
        this.state = state;
    }

    /**
     *
     * @param assistantCardModel The assistant card played that has to be removed from the player's deck
     */
    public void removeCard(AssistantCardModel assistantCardModel){
        this.deckAssistantCardModel.remove(assistantCardModel);
    }
}
