package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

public class DisplayIslandMessage extends DisplayMessage{
    private static final long serialVersionUID = 7835538492117196101L;
    private final IslandModel islandModel;
    private final int islandIndex;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display an island
     * Parameters are set by the constructor
     * @param nickname player's who's making the move
     * @param islandModel model of the island
     * @param islandIndex index of the island
     */

    public DisplayIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {
        super(nickname);
        this.islandModel = islandModel;
        this.islandIndex = islandIndex;
        this.objectDisplay = ObjectDisplay.ISLAND;
    }

    /**
     * @return the island model
     */

    public IslandModel getIslandModel() {
        return islandModel;
    }

    /**
     * @return the island index
     */

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
