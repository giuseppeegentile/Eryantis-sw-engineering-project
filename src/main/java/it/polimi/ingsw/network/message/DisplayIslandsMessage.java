package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class DisplayIslandsMessage extends Message{

    private static final long serialVersionUID = -3626482905376914288L;

    private final List<IslandModel> islandModels;
    public DisplayIslandsMessage(String nickname, List<IslandModel> islandsModel) {
        super(nickname, MessageType.DISPLAY);
        this.islandModels = islandsModel;
    }

    public List<IslandModel> getIslandModels() {
        return islandModels;
    }

    @Override
    public String toString() {
        return "DisplayIslandsMessage{" +
                "player=" + getNickname() +
                "islandModels=" + islandModels +
                '}';
    }
}
