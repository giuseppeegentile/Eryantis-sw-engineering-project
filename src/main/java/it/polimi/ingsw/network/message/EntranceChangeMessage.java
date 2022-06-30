package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

/**
 * Message sent when the player changed the entrance. Sends the updated entrance to the client.
 */
public class EntranceChangeMessage extends Message {
    private static final long serialVersionUID = 6149731718261769445L;
    private final List<ColorPawns> studentInEntrance;

    /**
     *
     * Parameters are set by the constructor.
     * @param nickname active player.
     * @param studentInEntrance list of updated students in entrance.
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
