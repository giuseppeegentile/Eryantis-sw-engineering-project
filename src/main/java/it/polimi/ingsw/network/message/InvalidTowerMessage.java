package it.polimi.ingsw.network.message;

public class InvalidTowerMessage extends Message{
    private static final long serialVersionUID = 4245063092764274017L;

    public InvalidTowerMessage(String nickname) {
        super(nickname, MessageType.ERROR);
    }


    @Override
    public String toString() {
        return "InvalidTowerMessage{" +
                "player=" + getNickname() +
                '}';
    }
}
