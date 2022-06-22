package it.polimi.ingsw.network.message;

public class PlayerNumberRequest extends Message {
    private static final long serialVersionUID = -5009707073097766703L;

    /**
     * Message shown as a request of the number of players
     */

    public PlayerNumberRequest() {
        super("SERVER_NICKNAME", MessageType.PLAYERNUMBER_REQUEST);
    }

    @Override
    public String toString() {
        return "PlayerNumberRequest{" +
                "player=" + getNickname() +
                '}';
    }
}

