package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class ReqStudentFromCardToHall extends Message{
    private static final long serialVersionUID = -4881307038377283680L;

    private final List<ColorPawns> studentsOnCard;

    /**
     * Message sent to move a student from the character's card to the hall
     * @param nickname current player
     * @param studentsOnCard students to move
     */

    public ReqStudentFromCardToHall(String nickname, List<ColorPawns> studentsOnCard) {
        super(nickname, MessageType.REQ_CARD_TO_HALL);
        this.studentsOnCard = studentsOnCard;
    }

    public List<ColorPawns> getStudentsOnCard() {
        return studentsOnCard;
    }

    @Override
    public String toString() {
        return "ReqStudentFromCardToHall{" +
                "player=" + getNickname() +
                ", studentsOnCard=" + studentsOnCard +
                '}';
    }
}
