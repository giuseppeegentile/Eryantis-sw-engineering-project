package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class StudentToHallMessage extends Message {
    private static final long serialVersionUID = -2680595509070093221L;
    private final List<ColorPawns> entrance;
    private final int numberStudentsToMove;

    public StudentToHallMessage(String player, List<ColorPawns> entrance, int numberStudentsToMove) {
        super(player, MessageType.REQ_ENTRANCE_TO_ISLAND);
        this.entrance =entrance;
        this.numberStudentsToMove = numberStudentsToMove;

    }

    public int getNumberStudentsToMove() {
        return numberStudentsToMove;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }

    @Override
    public String toString() {
        return "StudentToHallMessage{" +
                "player=" + getNickname() +
                ", students=" + entrance +
                ", numberStudentsToMove=" + numberStudentsToMove +
                '}';
    }
}
