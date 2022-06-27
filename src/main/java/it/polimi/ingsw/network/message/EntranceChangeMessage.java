package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class EntranceChangeMessage extends Message {
    private static final long serialVersionUID = 6149731718261769445L;
    private final List<ColorPawns> studentInEntrance;

    /**
     * Message sent when changing the player's entrance
     * Parameters are set by the constructor
     * @param nickname active player
     * @param studentInEntrance list of students in the entrance
     */

    public EntranceChangeMessage(String nickname, List<ColorPawns> studentInEntrance) {
        super(nickname, MessageType.ENTRANCE_CHANGES);
        this.studentInEntrance = studentInEntrance;
    }

    /**
     * @return the list of students in the entrance
     */

    public List<ColorPawns> getStudentInEntrance() {
        return studentInEntrance;
    }

    @Override
    public String toString() {
        return "EntranceChangeMessage{" +
                "player=" + getNickname() +
                ", studentInEntrance=" + studentInEntrance +
                '}';
    }
}
