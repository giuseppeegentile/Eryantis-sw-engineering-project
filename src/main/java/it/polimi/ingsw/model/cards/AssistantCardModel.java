package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

/**
 * Model class of the assistant card
 */

public class AssistantCardModel implements Serializable {

    private static final long serialVersionUID = -7197818777051204570L;
    private byte motherNatureMovement;
    private int priority;
    private boolean isPlayed;
    private PlayerModel owner;

    /**
     * Constructor for assistant card in the model: initializes isPlayed to the false value and owner to null value
     * @param priority The priority of the card. Will compete for the game turn management
     * @param motherNatureMovement The number of movements motherNature will be able to do with the card
     */
    public AssistantCardModel(int priority, byte motherNatureMovement) {
        this.priority = priority;
        this.motherNatureMovement = motherNatureMovement;
        this.isPlayed = false;
        this.owner = null;
    }

    /**
     *
     * @param owner The player to whom the card is assigned
     */
    public void setOwner(PlayerModel owner){
        this.owner = owner;
    }

    /**
     *
     * @return The player who owns the card
     */
    public PlayerModel getOwner(){
        return this.owner;
    }

    /**
     *
     * @return The number of movements motherNature will be able to do with the card
     */
    public byte getMotherNatureMovement() {
        return motherNatureMovement;
    }

    /**
     *
     * @return The priority of the card used in the game turn management
     */
    public int getPriority() {
        return priority;
    }

}