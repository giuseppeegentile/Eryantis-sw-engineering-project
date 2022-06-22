package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.PlayerModel;

public class ReqMoveMotherNatureMessage extends Message {
    private static final long serialVersionUID = -4800360044137978922L;

    private final byte maxMovementAllowed;
    private final PlayerModel player;

    /**
     * Message sent to move mother nature
     * @param player current player
     * @param maxMovementAllowed number of movements allowed
     */

    public ReqMoveMotherNatureMessage(PlayerModel player, byte maxMovementAllowed) {
        super(player.getNickname(), MessageType.MOVE_MOTHER_REQ);
        this.maxMovementAllowed = maxMovementAllowed;
        this.player = player;

    }

    public byte getMaxMovementAllowed() {
        return maxMovementAllowed;
    }

    @Override
    public String toString() {
        return "ReqMoveMotherNatureMessage{" +
                "player=" + getNickname() +
                ", maxMovementAllowed=" + maxMovementAllowed +
                '}';
    }

    public PlayerModel getPlayer() {
        return player;
    }
}
