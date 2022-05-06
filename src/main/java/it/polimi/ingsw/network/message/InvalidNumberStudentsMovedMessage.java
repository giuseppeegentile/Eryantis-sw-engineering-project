package it.polimi.ingsw.network.message;

public class InvalidNumberStudentsMovedMessage extends Message{

    private static final long serialVersionUID = -1491746583143505822L;

    public InvalidNumberStudentsMovedMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
