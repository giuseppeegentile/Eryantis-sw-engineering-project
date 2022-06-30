package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

/**
 *  Client to server. Message used by the client to request a login to the server.
 */

public class LoginRequest extends Message {

    private static final long serialVersionUID = -6343239452500134346L;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     */

    public LoginRequest(String nickname) {
        super(nickname, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "player=" + getNickname() +
                '}';
    }
}
