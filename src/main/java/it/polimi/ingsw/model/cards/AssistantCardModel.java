package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.PlayerModel;

public class AssistantCardModel {

    private byte motherNatureMovement;
    private int priority;
    private boolean isPlayed;
    private PlayerModel owner;

    public AssistantCardModel(int priority, byte motherNatureMovement) {
        this.priority = priority;
        this.motherNatureMovement = motherNatureMovement;
        this.isPlayed = false;
        this.owner = null;
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

    public void setMotherNatureMovement(byte motherNatureMovement) {
        this.motherNatureMovement = motherNatureMovement;
    }

    public byte getMotherNatureMovement() {
        return motherNatureMovement;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


}