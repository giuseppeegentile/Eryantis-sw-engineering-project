package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class EntranceChangeMessage extends Message {
    private static final long serialVersionUID = 6149731718261769445L;
    private final List<ColorPawns> studentInEntrance;
    public EntranceChangeMessage(String nickname, List<ColorPawns> studentInEntrance) {
        super(nickname, MessageType.ENTRANCE_CHANGES);
        this.studentInEntrance = studentInEntrance;
    }

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
