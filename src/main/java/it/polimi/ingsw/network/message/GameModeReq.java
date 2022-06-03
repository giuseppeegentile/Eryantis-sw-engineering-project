package it.polimi.ingsw.network.message;

public class GameModeReq extends Message {

    private static final long serialVersionUID = 4978053369068882855L;

    /**
     * Message shown when requesting the game mode
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

