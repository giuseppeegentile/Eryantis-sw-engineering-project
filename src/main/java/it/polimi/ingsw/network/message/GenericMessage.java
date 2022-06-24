package it.polimi.ingsw.network.message;



public class GenericMessage extends Message {

    private static final long serialVersionUID = 6714385171083931318L;
    private final String message;

    /**
     * Message to notify generic information to the user.
     * Parameters are set by the constructor
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
