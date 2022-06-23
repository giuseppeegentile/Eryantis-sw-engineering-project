package it.polimi.ingsw.network.message;
//to complete
public class InvalidNicknameMessage extends Message{
    private static final long serialVersionUID = -5602744464447019370L;

    /**
     * Message sent when a player chooses an invalid nickname
     * @param nickname nickname chosen
     */

    public InvalidNicknameMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }
}
