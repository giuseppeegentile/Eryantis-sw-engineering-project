package it.polimi.ingsw.network.message;

/**
 * Server to client. Message sent to start a turn
 */

public class StartTurnMessage extends Message{
    private static final long serialVersionUID = -1588872520077713701L;

    /**
<<<<<<< HEAD
     * Message sent to start a turn
=======
     * Default constructor
     * Parameter is set by the constructor
>>>>>>> main
     * @param nickname current player
     */

    public StartTurnMessage(String nickname) {
        super(nickname, MessageType.START_TURN);
    }
}
