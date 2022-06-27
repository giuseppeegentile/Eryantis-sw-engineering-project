package it.polimi.ingsw.network.message;


public class ErrorMessage extends Message {

    private static final long serialVersionUID = 3796309698593755714L;

    private final String error;

    /**
     * Message sent to display a general error
     *Parameters are set by the constructor
     * @param nickname current player
     * @param error error shown
     */

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    /**
     * @return the error
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
