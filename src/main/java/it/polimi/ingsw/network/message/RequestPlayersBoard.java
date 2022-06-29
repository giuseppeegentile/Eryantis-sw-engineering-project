package it.polimi.ingsw.network.message;

<<<<<<< HEAD
=======
/**
 * Client to server. Message sent to request the player's board
 */
>>>>>>> main

public class RequestPlayersBoard extends Message {
    private static final long serialVersionUID = -5224208291441233776L;

    /**
<<<<<<< HEAD
     * Message sent to request the player's board
=======
     * Default constructor
     * Parameter is set by the constructor
>>>>>>> main
     * @param nickname current player
     */

    public RequestPlayersBoard(String nickname) {
        super(nickname, MessageType.REQ_LOBBY);
    }
}