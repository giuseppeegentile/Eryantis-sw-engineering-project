package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.colors.ColorPawns.*;

public class PlayerModel implements Serializable {

    private static final long serialVersionUID = 530440588007500009L;
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
    private List<CharacterCardModel> characterDeck = new ArrayList<>();


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
        this.profs = new ArrayList<>();
        this.studentInEntrance = new ArrayList<>();
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
        this.profs = new ArrayList<>();
        this.studentInEntrance = new ArrayList<>();
        this.movementMotherNatureCurrentActionPhase = 0;
        this.studentInHall = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
    }

    public void addStudentsHall(List<ColorPawns> students){
        for(ColorPawns s: students){
            int oldValue = studentInHall.get(s);
            studentInHall.put(s, oldValue+1);
        }
    }

    public void addStudentsEntrance(List<ColorPawns> students){
        studentInEntrance.addAll(students);

    }

    public void removeStudentFromHall(List<ColorPawns> students){
        for(ColorPawns s: students){
            int oldValue = studentInHall.get(s);
            if(oldValue == 0)return;
            studentInHall.put(s, oldValue-1);
        }
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
    public StatePlayer getState(){
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

    /**
     *
     * @return The deck of assistant cards of the player
     */
    public List<AssistantCardModel> getDeckAssistantCardModel() {
        return this.deckAssistantCardModel;
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
        this.studentInEntrance = new ArrayList(studentInEntrance);
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
     * @param index The index of the player's deck played that has to be removed from the player's deck
     */
    public void removeCard(int index){
        this.getDeckAssistantCardModel().set(index, new AssistantCardModel(0, this, (byte) 0)); //remove the card..method remove is bugged for list
    }

    //**********************************
    //DA TESTARE
    public void removeStudentFromEntrance(List<ColorPawns> studentsToRemove){
        for (ColorPawns student : new ArrayList<>(studentsToRemove)) {
            for (ColorPawns entranceStudent : new ArrayList<>(studentInEntrance)) {
                if (student.equals(entranceStudent)) {
                    studentInEntrance.remove(student);
                    break;
                }
            }
        }
/*

Iterator<ColorPawns> iteratorStudentsToRemove = studentsToRemove.iterator();
        Iterator<ColorPawns> iteratorEntranceStudent = studentInEntrance.iterator();
        while(iteratorStudentsToRemove.hasNext()){
            while(iteratorEntranceStudent.hasNext()){
                if(iteratorEntranceStudent.next().equals(iteratorStudentsToRemove.next())){
                    iteratorEntranceStudent.remove();
                    break;
                }
            }
        }*/

    }

    public void addTowerToBoard(){
        this.towerNumber++;
    }

    public void removeTowerFromBoard(){
        if(this.towerNumber!= 0)
            this.towerNumber--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel that = (PlayerModel) o;
        return nickname.equals(that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public void assignCharacterDeck(List<CharacterCardModel> characterDeck) {
        this.characterDeck = characterDeck;
    }

    public List<CharacterCardModel> getCharacterDeck() {
        return characterDeck;
    }
}
