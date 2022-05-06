package it.polimi.ingsw.network.message;

//da controllare
public class InvalidMovementMessage extends Message{
    private static final long serialVersionUID = -7676451993818349286L;

    public InvalidMovementMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }
}
