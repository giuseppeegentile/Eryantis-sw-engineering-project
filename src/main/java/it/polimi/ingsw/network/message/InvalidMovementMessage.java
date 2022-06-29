package it.polimi.ingsw.network.message;

/**
 * Server to client. Message sent when a player makes an invalid movement
 */
public class InvalidMovementMessage extends Message{
    private static final long serialVersionUID = -7676451993818349286L;

    private final byte movementAllowed;
    private final byte movementInserted;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param movementAllowed movement that is allowed
     * @param movementInserted movement made by the player
     */

    public InvalidMovementMessage(String nickname, byte movementAllowed, byte movementInserted) {
        super(nickname, MessageType.ERROR);
        this.movementInserted = movementInserted;
        this.movementAllowed = movementAllowed;
    }

    /**
     * @return the movement selected
     */

    public byte getMovementInserted() {
        return movementInserted;
    }

    /**
     * @return the movement allowed
     */

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
