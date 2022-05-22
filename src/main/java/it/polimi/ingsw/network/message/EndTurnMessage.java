package it.polimi.ingsw.network.message;

public class EndTurnMessage extends Message{
    private static final long serialVersionUID = 1168012558305627763L;

    /**
     * Message shown to display the end of a player's turn
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
