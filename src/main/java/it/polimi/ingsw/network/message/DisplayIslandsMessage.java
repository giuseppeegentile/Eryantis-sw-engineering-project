package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class DisplayIslandsMessage extends DisplayMessage{

    private static final long serialVersionUID = -3626482905376914288L;
    private final ObjectDisplay objectDisplay;
    private final List<IslandModel> islandModels;

    public DisplayIslandsMessage(String nickname, List<IslandModel> islandsModel) {
        super(nickname);
        this.islandModels = islandsModel;
        this.objectDisplay = ObjectDisplay.ISLANDS;
    }

    public List<IslandModel> getIslandModels() {
        return this.islandModels;
    }

    @Override
    public String toString() {
        return "DisplayIslandsMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + objectDisplay +
                ", islandModels=" + islandModels +
                '}';
    }
}
