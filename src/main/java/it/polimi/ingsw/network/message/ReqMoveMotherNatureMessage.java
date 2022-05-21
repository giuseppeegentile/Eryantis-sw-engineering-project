package it.polimi.ingsw.network.message;

public class ReqMoveMotherNatureMessage extends Message {
    private static final long serialVersionUID = -4800360044137978922L;

    private final byte maxMovementAllowed;

    public ReqMoveMotherNatureMessage(String player, byte maxMovementAllowed) {
        super(player, MessageType.MOVE_MOTHER_REQ);
        this.maxMovementAllowed = maxMovementAllowed;

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
}