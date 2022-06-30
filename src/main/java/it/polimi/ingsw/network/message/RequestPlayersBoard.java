package it.polimi.ingsw.network.message;

/**
 * Client to server. Message sent to request the player's board
 */

public class RequestPlayersBoard extends Message {
    private static final long serialVersionUID = -5224208291441233776L;

    /**
     * Default constructor
     * Parameter is set by the constructor
     * @param nickname current player
     */

    public RequestPlayersBoard(String nickname) {
        super(nickname, MessageType.REQ_LOBBY);
    }
}