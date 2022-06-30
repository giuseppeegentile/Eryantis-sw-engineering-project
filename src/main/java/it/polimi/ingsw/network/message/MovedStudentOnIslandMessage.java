package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

/**
 * Client to server. Message sent when a player successfully moves a student to an island
 */

public class MovedStudentOnIslandMessage extends Message {
    private static final long serialVersionUID = 3175046343207320998L;

    private final List<ColorPawns> students;
    private final int indexIsland;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param students students moved
     * @param indexIsland index of the island in which students are moved
     */

    public MovedStudentOnIslandMessage(String nickname, List<ColorPawns> students, int indexIsland) {
        super(nickname, MessageType.PLAYER_MOVED_STUDENTS_ON_ISLAND);
        this.students = students;
        this.indexIsland = indexIsland;
    }

    /**
     * @return the index of the island chosen
     */

    public int getIslandIndex() {
        return indexIsland;
    }

    /**
     * @return the list of the students chosen
     */

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
