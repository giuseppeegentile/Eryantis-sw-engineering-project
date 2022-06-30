package it.polimi.ingsw.network.message;


/**
 * Message from the server to the client. Asks the game modality.
 */
public class GameModeReq extends Message {

    private static final long serialVersionUID = 4978053369068882855L;

    /**
     * Constructor, use the constructor of Message class with nickname SERVER_NICKNAME, because is a server message.
     */
    public GameModeReq() {
        super("SERVER_NICKNAME", MessageType.GAMEMODE_REQUEST);
    }

    @Override
    public String toString() {
        return "GameModeReq{" +
                "player=" + getNickname() +
                '}';
    }
}

