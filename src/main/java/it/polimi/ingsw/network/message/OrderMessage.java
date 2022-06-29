package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

/**
 * Server to client. Message sent to display the players' order
 */

public class OrderMessage extends Message{
    private static final long serialVersionUID = 9051276094255654184L;
    private final List<PlayerModel> order;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param order players' order
     */

    public OrderMessage(String nickname, List<PlayerModel> order) {
        super(nickname, MessageType.ORDER);
        this.order = order;
    }

    /**
     * @return the players order
     */

    public List<PlayerModel> getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "player=" + getNickname() +
                ", order=" + order +
                '}';
    }
}
