package it.polimi.ingsw.network.message;
//to complete
public class InvalidNicknameMessage extends Message{
    private static final long serialVersionUID = -5602744464447019370L;

    public InvalidNicknameMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }
}
