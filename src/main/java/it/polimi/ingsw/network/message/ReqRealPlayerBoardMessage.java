package it.polimi.ingsw.network.message;

public class ReqRealPlayerBoardMessage extends Message {
    private static final long serialVersionUID = 2368617064380355790L;

     private final String nickToSend;
    public ReqRealPlayerBoardMessage(String nick, String nickChosen) {
        super(nick, MessageType.REQ_PLAYER_BOARD);
        this.nickToSend = nickChosen;
    }

    public String getNickToSend() {
        return nickToSend;
    }
}
