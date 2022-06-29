package it.polimi.ingsw.network.message;

<<<<<<< HEAD

=======
/**
 * Message to notify generic information to the user.
 */
>>>>>>> main

public class GenericMessage extends Message {

    private static final long serialVersionUID = 6714385171083931318L;
    private final String message;

    /**
<<<<<<< HEAD
     * Message to notify generic information to the user.
     * Parameters are set by the constructor
=======
     * Default constructor
     * Parameters are set by the constructor
     * @param message message's content
>>>>>>> main
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
