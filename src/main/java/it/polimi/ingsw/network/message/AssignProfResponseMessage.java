package it.polimi.ingsw.network.message;

public class AssignProfResponseMessage extends  Message{
    private static final long serialVersionUID = -7897108701251560713L;

    public AssignProfResponseMessage(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
}
