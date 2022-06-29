package it.polimi.ingsw.network.message;

/**
 * Message sent to display a general error. Usually sent from server to client.
 */
public class ErrorMessage extends Message {

    private static final long serialVersionUID = 3796309698593755714L;

    private final String error;

    /**
<<<<<<< HEAD
     * Message sent to display a general error
     *Parameters are set by the constructor
     * @param nickname current player
     * @param error error shown
=======
     * Generic message error.
     * @param nickname current player.
     * @param error error shown.
>>>>>>> main
     */

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    /**
<<<<<<< HEAD
     * @return the error
=======
     * @return The String description error.
>>>>>>> main
     */

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "player=" + getNickname() +
                ", error=" + error +
                '}';
    }
}
