package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;

public class MovedFromCardToEntrance extends Message {

    private static final long serialVersionUID = -3022331922530053063L;
    private final List<ColorPawns> studentsFromCard;
    private final List<ColorPawns> studentsFromEntrance;

    /**
     * Message sent when moving students from a character card to the player's entrance
     * Parameters are set by the constructor
     * @param active current player
     * @param studentsFromCard list of students from the character card
     * @param studentsFromEntrance list of students from the entrance
     */

    public MovedFromCardToEntrance(String active, List<ColorPawns> studentsFromCard, List<ColorPawns> studentsFromEntrance) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.studentsFromCard = studentsFromCard;
        this.studentsFromEntrance = studentsFromEntrance;
    }

    /**
     * @return the list of students from the character card
     */

    public List<ColorPawns> getStudentsFromCard() {
        return studentsFromCard;
    }

    /**
     * @return the list of the chosen students from the entrance
     */

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
