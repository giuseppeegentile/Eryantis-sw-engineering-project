package it.polimi.ingsw.network.message;


import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.islands.IslandModel;

import java.io.Serializable;
/**
 * Class to be extended by every message type.
 * Server and clients will communicate with this class of message.
 */
public abstract class Message  implements Serializable {
    private static final long serialVersionUID = 6589184250663958343L;

    private final String nickname;
    private final MessageType messageType;
//    private final IslandModel islandModels;



    Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "player=" + nickname +
                ", messageType=" + messageType +
                '}';
    }
}
