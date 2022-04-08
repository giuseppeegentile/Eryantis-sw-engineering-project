package it.polimi.ingsw.model;

public class AssistantCardModel {

    private byte motherNatureMovement;
    private int priority;
    private boolean isPlayed;

    AssistantCardModel(int priority, byte motherNatureMovement) {
        this.priority = priority;
        this.motherNatureMovement = motherNatureMovement;
        this.isPlayed = false;
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