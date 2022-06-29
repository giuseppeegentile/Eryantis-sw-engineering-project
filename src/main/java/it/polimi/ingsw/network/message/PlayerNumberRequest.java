package it.polimi.ingsw.network.message;

/**
 * Server to client. Message sent as a request of the number of players.
 */

public class PlayerNumberRequest extends Message {
    private static final long serialVersionUID = -5009707073097766703L;

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

