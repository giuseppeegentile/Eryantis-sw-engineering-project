package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;

public class ChosenChangeEntranceHall extends Message {

    private static final long serialVersionUID = -8894935035871373037L;
    private final List<ColorPawns> studentsFromHall;
    private final List<ColorPawns> studentsFromEntrance;

    public ChosenChangeEntranceHall(String active, List<ColorPawns> studentsFromHall, List<ColorPawns> studentsFromEntrance) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.studentsFromEntrance = studentsFromEntrance;
        this.studentsFromHall = studentsFromHall;
    }


    public List<ColorPawns> getStudentsFromHall() {
        return studentsFromHall;
    }

    public List<ColorPawns> getStudentsFromEntrance() {
        return studentsFromEntrance;
    }

    @Override
    public String toString() {
        return "ChosenChangeEntranceHall{" +
                "player=" + getNickname() +
                ", studentsFromHall=" + studentsFromHall +
                ", studentsFromEntrance=" + studentsFromEntrance +
                '}';
    }
}
