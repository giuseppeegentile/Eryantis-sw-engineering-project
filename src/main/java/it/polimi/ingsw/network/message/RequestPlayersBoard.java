package it.polimi.ingsw.network.message;


public class RequestPlayersBoard extends Message {
    private static final long serialVersionUID = -5224208291441233776L;

    public RequestPlayersBoard(String nickname) {
        super(nickname, MessageType.REQ_LOBBY);
    }
}