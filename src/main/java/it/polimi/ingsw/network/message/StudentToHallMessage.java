package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

/**
 * Client to server.Message sent to move a student from the entrance to the hall
 */

public class StudentToHallMessage extends Message {
    private static final long serialVersionUID = -2680595509070093221L;
    private final List<ColorPawns> entrance;
    private final int numberStudentsToMove;

    /**
     * Default constructor.
     * Parameters are set by the constructor
     * @param player current player
     * @param entrance player's entrance
     * @param numberStudentsToMove number of students to move
     */

    public StudentToHallMessage(String player, List<ColorPawns> entrance, int numberStudentsToMove) {
        super(player, MessageType.REQ_ENTRANCE_TO_ISLAND);
        this.entrance =entrance;
        this.numberStudentsToMove = numberStudentsToMove;

    }

    /**
     * @return the number of students to move
     */

    public int getNumberStudentsToMove() {
        return numberStudentsToMove;
    }

    /**
     * @return the player's entrance
     */

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
