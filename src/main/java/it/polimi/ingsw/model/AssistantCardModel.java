package it.polimi.ingsw.model;

public class AssistantCardModel {

    private byte motherNatureMovement;
    private int priority;
    private boolean isPlayed;
    private PlayerModel owner;

    AssistantCardModel(int priority, byte motherNatureMovement) {
        this.priority = priority;
        this.motherNatureMovement = motherNatureMovement;
        this.isPlayed = false;
    }

    AssistantCardModel(int priority, byte motherNatureMovement, PlayerModel owner) {
        this.priority = priority;
        this.motherNatureMovement = motherNatureMovement;
        this.isPlayed = false;
        this.owner = owner;
    }

    public void setOwner(PlayerModel owner){
        this.owner = owner;
    }

    public PlayerModel getOwner(){
        return this.owner;
    }

    private void setPlayed() {
        isPlayed = true;
    }

    private boolean isPlayed() {
        return isPlayed;
    }

    public byte getMotherNatureMovement() {
        return motherNatureMovement;
    }

    public int getPriority() {
        return priority;
    }


}