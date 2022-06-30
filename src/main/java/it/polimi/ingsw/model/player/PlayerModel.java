package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.colors.ColorPawns.*;

public class PlayerModel implements Serializable {

    private static final long serialVersionUID = 530440588007500009L;
    private String nickname;
    private List<ColorPawns> profs = null;
    private int numProfs;
    private int towerNumber;
    private ColorTower colorTower;
    private List<AssistantCardModel> deckAssistantCardModel;
    private Map<ColorPawns, Integer> studentInHall = new HashMap<>();
    private List<ColorPawns> studentInEntrance;
    private byte movementMotherNatureCurrentActionPhase;
    private int coins;
    private boolean isFirst = true;
    private List<CharacterCardModel> characterDeck = new ArrayList<>();


    /**
     * Initialize coins, numProf and mother nature movement to zero. Also set the map for the student hall
     * @param nickname nickname choose by player
     * @param colorTower tower color assigned to a player, identifier for the team in 4-player version
     */
    public PlayerModel(String nickname, ColorTower colorTower){
        this.nickname = nickname;
        this.colorTower = colorTower;
        this.coins = 1;
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

    /**
     * Constructor for player in the model: initialize coins, numProf and mother nature movement to zero. Also set the map for the student hall
     * @param nickname nickname choose by player
     */
    public PlayerModel(String nickname){
        this.nickname = nickname;
        this.coins = 1;
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

    /**
     *
     * @param students The list of students to add to the student's hall
     */
    public void addStudentsHall(List<ColorPawns> students){
        for(ColorPawns s: students){
            int oldValue = studentInHall.get(s);
            studentInHall.put(s, oldValue+1);
        }
    }

    /**
     *
     * @param students The list of students to add to the student's entrance
     */
    public void addStudentsEntrance(List<ColorPawns> students){
        studentInEntrance.addAll(students);

    }

    /**
     *
     * @param students The list of the students to remove from the student's hall
     */
    public void removeStudentFromHall(List<ColorPawns> students){
        for(ColorPawns s: students){
            int oldValue = studentInHall.get(s);
            if(oldValue == 0)return;
            studentInHall.put(s, oldValue-1);
        }
    }

    /**
     * Costructor for player in the model: initialize colorTower to null.
     */
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
        if(this.profs!= null)
            return this.profs;
        return new ArrayList<>();
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
        return this.profs.contains(prof);
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

    /**
     *
     * @return The coins owned by the player
     */
    public int getCoins(){
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

    //**********************************
    //DA TESTARE

    /**
     *
     * @param studentsToRemove The list of the students to remove from the entrance
     */
    public void removeStudentFromEntrance(List<ColorPawns> studentsToRemove){
        for (ColorPawns student : new ArrayList<>(studentsToRemove)) {
            for (ColorPawns entranceStudent : new ArrayList<>(studentInEntrance)) {
                if (student.equals(entranceStudent)) {
                    studentInEntrance.remove(student);
                    break;
                }
            }
        }
    }

    /**
     * It adds a tower to the player board
     */
    public void addTowerToBoard(){
        this.towerNumber++;
    }

    /**
     * It removes a tower from the player board
     */
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

    /**
     *
     * @param characterDeck The list of the character card to give to the player
     */
    public void assignCharacterDeck(List<CharacterCardModel> characterDeck) {
        this.characterDeck = characterDeck;
    }

    /**
     *
     * @return The list of the character card owned by the player
     */
    public List<CharacterCardModel> getCharacterDeck() {
        return characterDeck;
    }

    public void setIsFirst(){
        isFirst = false;
    }

    public boolean isFirst() {
        return isFirst;
    }
}
