package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class StudentToHallMessage extends  Message{
    private static final long serialVersionUID = 5223442775735413916L;
    private final List<ColorPawns> students;


    public StudentToHallMessage(String nickname, List<ColorPawns> students) {
        super(nickname, MessageType.MOVE);
        this.students = students;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "StudentToHallMessage{" +
                "player=" + getNickname() +
                ", students=" + students +
                '}';
    }
}
