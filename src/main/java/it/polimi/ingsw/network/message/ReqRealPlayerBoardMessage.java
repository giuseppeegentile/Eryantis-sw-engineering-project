package it.polimi.ingsw.network.message;

<<<<<<< HEAD
=======
/**
 * Server to client. Message sent to show the another player's board
 */

>>>>>>> main
public class ReqRealPlayerBoardMessage extends Message {
    private static final long serialVersionUID = 2368617064380355790L;

    private final String nickToSend;

    /**
<<<<<<< HEAD
     * Message sent to show the another player's board
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param nick current player
     * @param nickChosen player chosen
     */

    public ReqRealPlayerBoardMessage(String nick, String nickChosen) {
        super(nick, MessageType.REQ_PLAYER_BOARD);
        this.nickToSend = nickChosen;
    }

    /**
     * @return the nickname to send
     */

    public String getNickToSend() {
        return nickToSend;
    }
}