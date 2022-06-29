package it.polimi.ingsw.network.message;

/**
 *  Server to client. Message sent when a player chooses an invalid nickname
 */
public class InvalidNicknameMessage extends Message{
    private static final long serialVersionUID = -5602744464447019370L;

    /**
<<<<<<< HEAD
     * Message sent when a player chooses an invalid nickname
=======
     * Default constructor
     * Parameter is set by the constructor
>>>>>>> main
     * @param nickname nickname chosen
     */

    public InvalidNicknameMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }
}
