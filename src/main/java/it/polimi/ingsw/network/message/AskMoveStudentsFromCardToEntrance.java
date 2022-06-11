package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;

public class AskMoveStudentsFromCardToEntrance extends Message {

    private static final long serialVersionUID = 8920514414226827005L;
    private final List<ColorPawns> studentsOnCard;
    private final List<ColorPawns> entrance;

    public AskMoveStudentsFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance) {
        super(active, MessageType.MOVE_FROM_CARD_TO_HALL);
        this.studentsOnCard = studentsOnCard;
        this.entrance = entrance;
    }

    public List<ColorPawns> getStudentsOnCard() {
        return studentsOnCard;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }

    @Override
    public String toString() {
        return "AskMoveStudentsFromCardToEntrance{" +
                "player=" + getNickname() +
                ", studentsOnCard=" + studentsOnCard +
                ", entrance=" + entrance+
                '}';
    }
}
