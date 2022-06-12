package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class StudentToIslandMessage extends Message{
    private static final long serialVersionUID = 1729951793698626264L;

    private final List<ColorPawns> entrance;
    private final List<IslandModel> islands;

    public StudentToIslandMessage(String nickname, List<ColorPawns> entrance,List<IslandModel> islands) {
        super(nickname, MessageType.REQ_ENTRANCE_TO_HALL);
        this.entrance = entrance;
        this.islands = islands;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }


    @Override
    public String toString() {
        return "StudentToIslandMessage{" +
                "player=" + getNickname() +
                ", students=" + entrance +
                ", islands=" + islands +
                '}';
    }

    public List<IslandModel> getIslands() {
        return islands;
    }
}
