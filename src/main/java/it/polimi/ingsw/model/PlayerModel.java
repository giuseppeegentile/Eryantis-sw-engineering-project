package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.ColorPawns.*;

public class PlayerModel {
    private String nickname;
    private List<ColorPawns> profs;
    private int numProfs;
    private int towerNumber; //da settare con il controller, strategy
    private ColorTower colorTower;
    private List<AssistantCardModel> deckAssistantCardModel;
    private Map<ColorPawns, Integer> studentInHall;
    private List<ColorPawns> studentInEntrance;
    private StatePlayer state;
    private byte movementMotherNatureCurrentActionPhase;
    private int coins;

    PlayerModel(String nickname, ColorTower colorTower, int coins){
        this.nickname = nickname;
        this.colorTower = colorTower;
        this.coins = coins;
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

    public int getNumProfs(){
        return this.numProfs;
    }

    public void incrementNumProfs(){
        this.numProfs += 1;
    }

    public void decrementNumProfs(){
        if(this.numProfs > 0)
            this.numProfs -= 1;
    }

    public void removeProf(int index){
        this.profs.remove(index);
    }

    public List<ColorPawns> getProfs(){
        return this.profs;
    }

    protected String getNickname(){
        return this.nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    private StatePlayer getState(){
        return this.state;
    }

    public void setProfs(ColorPawns profToAdd){
        if(profs == null)
            profs = new ArrayList<>();
        profs.add(profToAdd);
    }

    private static void playCard(){ }

    private int getCoins(){
        return this.coins;
    }

    public void removeCoins(int coinsUsed){
        this.coins -=coinsUsed;
    }

    public void addCoins(){
        this.coins +=1 ;
    }

    public void setCoins(){
        this.coins = 1;
    }

    public void setStudentHall(Map<ColorPawns, Integer> studentInHall){
        this.studentInHall = studentInHall;
    }

    public Map<ColorPawns, Integer> getStudentInHall(){
        return this.studentInHall;
    }

    public int getTowerNumber(){
        return this.towerNumber;
    }

    public void setColorTower(ColorTower colorTower, int towerNumber){
        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }

    public byte getMovementMotherNatureCurrentActionPhase() {
        return movementMotherNatureCurrentActionPhase;
    }

    public void setMovementMotherNatureCurrentActionPhase(byte movementMotherNatureCurrentActionPhase) {
        this.movementMotherNatureCurrentActionPhase = movementMotherNatureCurrentActionPhase;
    }

    public List<AssistantCardModel> getDeckAssistantCardModel() {
        return deckAssistantCardModel;
    }

    public void setDeckAssistantCardModel(List<AssistantCardModel> deckAssistantCardModel) {
        this.deckAssistantCardModel = deckAssistantCardModel;
    }

    public ColorTower getColorTower() {
        return colorTower;
    }

    public void setColorTower(ColorTower colorTower) {
        this.colorTower = colorTower;
    }

    public List<ColorPawns> getStudentInEntrance() {
        return studentInEntrance;
    }

    public void setStudentInEntrance(List<ColorPawns> studentInEntrance) {
        this.studentInEntrance = studentInEntrance;
    }

    public void setState(StatePlayer state) {
        this.state = state;
    }
}
