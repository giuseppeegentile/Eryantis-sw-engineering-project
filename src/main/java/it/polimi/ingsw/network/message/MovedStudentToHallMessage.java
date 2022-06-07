package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class MovedStudentToHallMessage extends  Message{
    private static final long serialVersionUID = 5223442775735413916L;
    private final List<ColorPawns> students;

    public MovedStudentToHallMessage(String nickname, List<ColorPawns> students) {
        super(nickname, MessageType.PLAYER_MOVED_STUDENTS_ON_HALL);
        this.students = students;
    }


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
