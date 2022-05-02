package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class StudentToIslandMessage extends Message{
    private static final long serialVersionUID = 1729951793698626264L;

    private final List<ColorPawns> students;
    private final IslandModel islandModel;
    private final int indexIsland;

    public StudentToIslandMessage(String nickname, List<ColorPawns> students, int indexIsland, MessageType messageType) {
        super(nickname, messageType);
        this.students = students;
        this.islandModel = GameModel.getInstance().getIslandsModel().get(indexIsland);
        this.indexIsland = indexIsland;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    public IslandModel getIslandModel() {
        return islandModel;
    }

    @Override
    public String toString() {
        return "StudentToIslandMessage{" +
                "nickname=" + getNickname() +
                "students=" + students +
                ", islandModel=" + islandModel +
                '}';
    }

    public int getIndexIsland() {
        return indexIsland;
    }
}
