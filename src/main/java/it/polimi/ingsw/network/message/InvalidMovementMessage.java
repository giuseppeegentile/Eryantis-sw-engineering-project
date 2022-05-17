package it.polimi.ingsw.network.message;

//da controllare
public class InvalidMovementMessage extends Message{
    private static final long serialVersionUID = -7676451993818349286L;

    private final byte movementAllowed;
    private final byte movementInserted;

    public InvalidMovementMessage(String nickname, byte movementAllowed, byte movementInserted) {
        super(nickname, MessageType.ERROR);
        this.movementInserted = movementInserted;
        this.movementAllowed = movementAllowed;
    }

    public byte getMovementInserted() {
        return movementInserted;
    }

    public byte getMovementAllowed() {
        return movementAllowed;
    }

    @Override
    public String toString() {
        return "InvalidMovementMessage{" +
                "player" + getNickname() +
                ", movementAllowed=" + movementAllowed +
                ", movementInserted=" + movementInserted +
                '}';
    }
}
