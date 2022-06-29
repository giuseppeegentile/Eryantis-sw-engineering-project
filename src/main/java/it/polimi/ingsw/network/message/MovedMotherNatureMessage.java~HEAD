package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.PlayerModel;


public class MovedMotherNatureMessage extends Message{

    private static final long serialVersionUID = 1566360713494368537L;

    private final String playerModel;

    private final byte movement;

    /**
     * Message sent when moving mother nature
     * Parameters are set by the constructor
     * @param player current player
     * @param movement movement chosen
     */

    public MovedMotherNatureMessage(String player, byte movement){
        super(player, MessageType.PLAYER_MOVED_MOTHER);
        this.playerModel = player;
        this.movement = movement;
    }

    /**
     * @return the movement chosen
     */

    public byte getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "player=" + playerModel +
                ", motherMovement=" + movement +
                '}';
    }
}


