package it.polimi.ingsw.network.message;


public class RequestPlayersBoard extends Message {
    private static final long serialVersionUID = -5224208291441233776L;

    /**
     * Message sent to request the player's board
     * @param nickname current player
     */

    public RequestPlayersBoard(String nickname) {
        super(nickname, MessageType.REQ_LOBBY);
    }
}