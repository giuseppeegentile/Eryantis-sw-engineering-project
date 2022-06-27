package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;
import java.util.List;


public class AskMoveBanCardMessage extends Message {

    private static final long serialVersionUID = 2849430098890890060L;
    private final List<IslandModel> islands;

    /**
     * Message sent to ask to ban a move after using the character card
     * Parameters are set by the constructor
     * @param active active player
     * @param islands list of islands
     */

    public AskMoveBanCardMessage(String active, List<IslandModel> islands) {
        super(active, MessageType.ASK_MOVE_BAN_CARD);
        this.islands = islands;
    }

    /**
     * @return the list of islands
     */

    public List<IslandModel> getIslands() {
        return islands;
    }

    @Override
    public String toString() {
        return "askMoveBanCardMessage{" +
                "player=" + getNickname() +
                ", islands=" + islands +
                '}';
    }
}
