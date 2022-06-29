package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;
<<<<<<< HEAD

=======
/**
 * Sent from server to client after a client played the effect that allows to move card's student to entrance.
 */
>>>>>>> main
public class AskMoveStudentsFromCardToEntrance extends Message {

    private static final long serialVersionUID = 8920514414226827005L;
    private final List<ColorPawns> studentsOnCard;
    private final List<ColorPawns> entrance;

    /**
     * Message sent to ask to move students from the character card to the entrance.
<<<<<<< HEAD
     * Parameters are set by the constructor
     * @param active active player
=======
     * @param active player that played the effect
>>>>>>> main
     * @param studentsOnCard list of students on the card
     * @param entrance player's entrance
     */

    public AskMoveStudentsFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance) {
        super(active, MessageType.MOVE_FROM_CARD_TO_HALL);
        this.studentsOnCard = studentsOnCard;
        this.entrance = entrance;
    }

    /**
     * @return the list of students on the card
     */

    public List<ColorPawns> getStudentsOnCard() {
        return studentsOnCard;
    }

    /**
     * @return the player's entrance
     */

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
