package it.polimi.ingsw.network.message;
//to complete
public class InvalidNicknameMessage extends Message{
    InvalidNicknameMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }
}
