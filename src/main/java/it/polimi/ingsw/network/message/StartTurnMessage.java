package it.polimi.ingsw.network.message;

public class StartTurnMessage extends Message{
    private static final long serialVersionUID = -1588872520077713701L;

    /**
     * Message sent to start a turn
     * @param nickname current player
     */

    public StartTurnMessage(String nickname) {
        super(nickname, MessageType.START_TURN);
    }
}
