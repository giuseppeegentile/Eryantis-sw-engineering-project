package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.*;

import static it.polimi.ingsw.model.ColorPawns.*;

public class PlayerModel {
    private String nickname;
    private List<ColorPawns> profs = null;
    private int numProfs;
    private int towerNumber; //da settare con il controller, strategy
    private ColorTower colorTower;
    private List<AssistantCardModel> deckAssistantCardModel;
    private Map<ColorPawns, Integer> studentInHall = new HashMap<>();
    private List<ColorPawns> studentInEntrance;
    private StatePlayer state;
    private byte movementMotherNatureCurrentActionPhase;
    private int coins;


    /**
     * Costructor for player in the model: initialize coins, numProf and mother nature movement to zero. Also set se map for the student hall
     * @param nickname nickname choose by player
     * @param colorTower tower color assigned to a player, identifier for the team in 4-player version
     *
     *
     */
    public PlayerModel(String nickname, ColorTower colorTower){
        this.nickname = nickname;
        this.colorTower = colorTower;
        this.coins = 0;
        this.numProfs = 0;
        this.movementMotherNatureCurrentActionPhase = 0;
        this.studentInHall =new HashMap<>( Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
    }

    public PlayerModel(String nickname){
        this.nickname = nickname;
        this.coins = 0;
        this.numProfs = 0;
        this.movementMotherNatureCurrentActionPhase = 0;
        this.studentInHall = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
    }

    public PlayerModel(){
        this.colorTower = ColorTower.NULL;
    }


    /**
     *
     * @return the number of prof owned by the player
     */
    public int getNumProfs(){
        if(this.profs == null) return 0;
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
    public List<ColorPawns> getProfs(){
        return this.profs;
    }

    /**
     *
     * @param profToAdd prof to add to the set of prof owned
     */
    public void addProf(ColorPawns profToAdd){
        if(this.profs == null)
            this.profs = new ArrayList<>();
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
    public void removeStudentFromEntrance(ColorPawns studentToRemove) {
        this.studentInEntrance.remove(studentToRemove);
        for(int i = 0; i < this.studentInEntrance.size() && !this.studentInEntrance.get(i).equals(studentToRemove); i++){
            if(this.studentInEntrance.get(i).equals(studentToRemove)) {
                this.studentInEntrance.remove(i);

            }
        }
    }

    private static void playCard(){ }

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
     * add one coins to the coins value
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
     * @param colorTower The color of the tower choose by the player
     * @param towerNumber The number of tower in the board, which depends by the number of player
     */
    public void setTowers(ColorTower colorTower, int towerNumber){
        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }

    /**
     *
     * @return The movement of mother nature in the current action phase. It depends by the card played.
     */
    public byte getMovementMotherNatureCurrentActionPhase() {
        return movementMotherNatureCurrentActionPhase;
    }

    /**
     *
     * @param movementMotherNatureCurrentActionPhase The movement of mother nature in the current action phase. It depends by the card played.
     */
    public void setMovementMotherNatureCurrentActionPhase(byte movementMotherNatureCurrentActionPhase) {
        this.movementMotherNatureCurrentActionPhase = movementMotherNatureCurrentActionPhase;
    }

    public List<AssistantCardModel> getDeckAssistantCardModel() {
        return this.deckAssistantCardModel;
    }

    public void setDeckAssistantCardModel(List<AssistantCardModel> deckAssistantCardModel) {
        this.deckAssistantCardModel = deckAssistantCardModel;
    }

    public ColorTower getColorTower() {
        return colorTower;
    }

    public void setTowers(ColorTower colorTower) {
        this.colorTower = colorTower;
    }

    public List<ColorPawns> getStudentInEntrance() {
        return studentInEntrance;
    }

    public void setStudentInEntrance(List<ColorPawns> studentInEntrance) {
        this.studentInEntrance = new ArrayList(studentInEntrance);
    }

    public void setState(StatePlayer state) {
        this.state = state;
    }


    public void removeCard(int index){
        this.getDeckAssistantCardModel().set(index, new AssistantCardModel(0, (byte) 0)); //remove the card..method remove is bugged for list

    }
}
