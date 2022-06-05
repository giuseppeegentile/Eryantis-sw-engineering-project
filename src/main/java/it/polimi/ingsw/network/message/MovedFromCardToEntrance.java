package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;

public class MovedFromCardToEntrance extends Message {

    private static final long serialVersionUID = -3022331922530053063L;
    private final List<ColorPawns> studentsFromCard;
    private final List<ColorPawns> studentsFromEntrance;

    public MovedFromCardToEntrance(String active, List<ColorPawns> studentsFromCard, List<ColorPawns> studentsFromEntrance) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.studentsFromCard = studentsFromCard;
        this.studentsFromEntrance = studentsFromEntrance;
    }

    public List<ColorPawns> getStudentsFromCard() {
        return studentsFromCard;
    }

    public List<ColorPawns> getStudentsFromEntrance() {
        return studentsFromEntrance;
    }

    @Override
    public String toString() {
        return "MovedFromCardToEntrance{" +
                "player=" + getNickname() +
                ", studentsFromCard=" + studentsFromCard +
                ", studentsFromEntrance=" + studentsFromEntrance +
                '}';
    }
}
