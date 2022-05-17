package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

public class DisplayIslandMessage extends DisplayMessage{
    private static final long serialVersionUID = 7835538492117196101L;
    private final IslandModel islandModel;
    private final int islandIndex;
    private final ObjectDisplay objectDisplay;

    public DisplayIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {
        super(nickname);
        this.islandModel = islandModel;
        this.islandIndex = islandIndex;
        this.objectDisplay = ObjectDisplay.ISLAND;
    }

    public IslandModel getIslandModel() {
        return islandModel;
    }

    public int getIslandIndex() {
        return islandIndex;
    }

    @Override
    public String toString() {
        return "DisplayIslandMessage{" +
                "player"+ getNickname()+
                ", islandModel=" + islandModel +
                ", objectDisplay=" + objectDisplay +
                ", islandIndex=" + islandIndex +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
