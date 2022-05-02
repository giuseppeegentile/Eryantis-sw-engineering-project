package it.polimi.ingsw.network.message;

public class NextPlayerToPlayMessage extends Message{
    private static final long serialVersionUID = -3038175425035241783L;

    NextPlayerToPlayMessage(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
}
