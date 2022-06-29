package it.polimi.ingsw.network.message;

/**
 *  Server to client. Message sent when a player moves the wrong number of students
 */

public class InvalidNumberStudentsMovedMessage extends Message{

    private static final long serialVersionUID = -1491746583143505822L;

    /**
     * Default constructor
     * Parameter is set by the constructor
     * @param nickname current player
     */

    public InvalidNumberStudentsMovedMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
