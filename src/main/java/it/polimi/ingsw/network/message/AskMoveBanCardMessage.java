package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;
import java.util.List;


public class AskMoveBanCardMessage extends Message {

    private static final long serialVersionUID = 2849430098890890060L;
    private final List<IslandModel> islands;

    public AskMoveBanCardMessage(String active, List<IslandModel> islands) {
        super(active, MessageType.ASK_MOVE_BAN_CARD);
        this.islands = islands;
    }

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
