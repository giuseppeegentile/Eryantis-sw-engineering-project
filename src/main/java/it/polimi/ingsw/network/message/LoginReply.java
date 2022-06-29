package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameModel;

<<<<<<< HEAD
=======
/**
 * Server to client. Message used to confirm or discard a login request of a client.
 */
>>>>>>> main

public class LoginReply extends Message {

    private static final long serialVersionUID = -1423312065079102467L;
    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    /**
<<<<<<< HEAD
     * Message used to confirm or discard a login request of a client.
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param connectionSuccessful boolean to verify whether the connection is successful or not
     * @param nicknameAccepted boolean to verify whether the nickname is accepted or not
     */
    public LoginReply(boolean nicknameAccepted, boolean connectionSuccessful) {
        super("SERVER_NICKNAME", MessageType.LOGIN_REPLY);
        this.nicknameAccepted = nicknameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }


    public boolean isNicknameAccepted() {
        return nicknameAccepted;
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    @Override
    public String toString() {
        return "LoginReply{" +
                "player=" + getNickname() +
                ", nicknameAccepted=" + nicknameAccepted +
                ", connectionSuccessful=" + connectionSuccessful +
                '}';
    }
}
