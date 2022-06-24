package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;

public class ChosenChangeEntranceHall extends Message {

    private static final long serialVersionUID = -8894935035871373037L;
    private final List<ColorPawns> studentsFromHall;
    private final List<ColorPawns> studentsFromEntrance;

    /**
     * Message sent to set the chosen students to exchange from hall to entrance
     * Parameters are set by the constructor
     * @param active active player
     * @param studentsFromHall list of the students from the hall
     * @param studentsFromEntrance list of students from the entrance
     */

    public ChosenChangeEntranceHall(String active, List<ColorPawns> studentsFromHall, List<ColorPawns> studentsFromEntrance) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.studentsFromEntrance = studentsFromEntrance;
        this.studentsFromHall = studentsFromHall;
    }

    /**
     * @return the list of the students from the hall chosen
     */

    public List<ColorPawns> getStudentsFromHall() {
        return studentsFromHall;
    }

    /**
     * @return the list of the students from the entrance chosen
     */

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
