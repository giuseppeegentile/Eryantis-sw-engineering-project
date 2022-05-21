package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class MovedStudentOnIslandMessage extends Message {
    private static final long serialVersionUID = 3175046343207320998L;

    private final List<ColorPawns> students;
    private final int indexIsland;

    public MovedStudentOnIslandMessage(String nickname, List<ColorPawns> students, int indexIsland) {
        super(nickname, MessageType.PLAYER_MOVED_STUDENTS_ON_ISLAND);
        this.students = students;
        this.indexIsland = indexIsland;
    }

    public int getIslandIndex() {
        return indexIsland;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "MovedStudentOnIslandMessage{" +
                "player=" + getNickname() +
                ", students=" + students +
                ", indexIsland=" + indexIsland +
                '}';
    }
}
