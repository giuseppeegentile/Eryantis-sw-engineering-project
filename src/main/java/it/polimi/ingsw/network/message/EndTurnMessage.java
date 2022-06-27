package it.polimi.ingsw.network.message;

public class EndTurnMessage extends Message{
    private static final long serialVersionUID = 1168012558305627763L;

    /**
     * Message sent to display the end of a player's turn
     * Parameters are set by the constructor
     * @param nickname current player
     */

    public EndTurnMessage(String nickname) {
        super(nickname, MessageType.END_TURN);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
