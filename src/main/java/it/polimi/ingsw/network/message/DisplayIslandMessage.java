package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

public class DisplayIslandMessage extends Message{


    private static final long serialVersionUID = 7835538492117196101L;
    private final IslandModel islandModel;
    private final int islandIndex;

    public DisplayIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {
        super(nickname, MessageType.DISPLAY);
        this.islandModel = islandModel;
        this.islandIndex = islandIndex;
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
                "islandModel=" + islandModel +
                ", islandIndex=" + islandIndex +
                '}';
    }
}
