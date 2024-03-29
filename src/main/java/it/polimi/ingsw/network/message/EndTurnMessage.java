package it.polimi.ingsw.network.message;

/**
 * Message sent to display the end of a player's turn. Sent by server.
 */
public class EndTurnMessage extends Message{
    private static final long serialVersionUID = 1168012558305627763L;

    /**
     * Use the super constructor of Message class
     * @param nickname player that end to play this turn
     */
    public EndTurnMessage(String nickname) {
        super(nickname, MessageType.END_TURN);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
