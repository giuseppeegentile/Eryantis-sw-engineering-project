package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;
import java.util.List;


public class AskExtraGetInfluenceMessage extends Message {

    private static final long serialVersionUID = 8524765326714118718L;
    private final List<IslandModel> islands;

    public AskExtraGetInfluenceMessage(String active, List<IslandModel> islands) {
        super(active, MessageType.ASK_EXTRA_GET_INFLUENCE);
        this.islands = islands;
    }


    public List<IslandModel> getIslands() {
        return islands;
    }

    @Override
    public String toString() {
        return "askExtraGetInfluenceMessage{" +
                "player=" + getNickname() +
                ", islands=" + islands +
                '}';
    }
}
