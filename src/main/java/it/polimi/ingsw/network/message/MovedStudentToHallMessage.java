package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class MovedStudentToHallMessage extends  Message{
    private static final long serialVersionUID = 5223442775735413916L;
    private final List<ColorPawns> students;

    /**
     * Message sent when a student is successfully moved into the player's hall
     * Parameters are set by the constructor
     * @param nickname current player
     * @param students students moved
     */

    public MovedStudentToHallMessage(String nickname, List<ColorPawns> students) {
        super(nickname, MessageType.PLAYER_MOVED_STUDENTS_ON_HALL);
        this.students = students;
    }

    /**
     * @return the list of students
     */

    public List<ColorPawns> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "MovedStudentToHallMessage{" +
                "player=" + getNickname() +
                ", students=" + students +
                '}';
    }
}
