package it.polimi.ingsw.network.message;

/**
 * Message to notify generic information to the user.
 */

public class GenericMessage extends Message {

    private static final long serialVersionUID = 6714385171083931318L;
    private final String message;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param message message's content
     */

    public GenericMessage(String message) {
        super("SERVER_NICK", MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "player=" + getNickname() +
                ", message=" + message +
                '}';
    }
}
