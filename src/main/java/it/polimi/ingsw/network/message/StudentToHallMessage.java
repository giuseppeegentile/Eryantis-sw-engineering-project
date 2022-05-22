package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class StudentToHallMessage extends Message {
    private static final long serialVersionUID = -2680595509070093221L;
    private final List<ColorPawns> students;
    private final int numberStudentsToMove;

    /**
     * Message shown when a player moves a student to the hall
     * @param player current player
     * @param students student moved
     * @param numberStudentsToMove number of students to move
     */

    public StudentToHallMessage(String player, List<ColorPawns> students, int numberStudentsToMove) {
        super(player, MessageType.MOVE);
        this.students =students;
        this.numberStudentsToMove = numberStudentsToMove;

    }

    public int getNumberStudentsToMove() {
        return numberStudentsToMove;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "StudentToHallMessage{" +
                "player=" + getNickname() +
                ", students=" + students +
                ", numberStudentsToMove=" + numberStudentsToMove +
                '}';
    }
}
