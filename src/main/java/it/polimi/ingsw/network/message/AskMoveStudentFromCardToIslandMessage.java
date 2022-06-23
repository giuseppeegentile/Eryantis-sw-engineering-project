package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class AskMoveStudentFromCardToIslandMessage extends Message {

    private static final long serialVersionUID = -2436545940503369104L;
    private final List<IslandModel> islands;
    private final List<ColorPawns> studentsOnCard;

    /**
     * Message sent to ask to move a student from a card to an island
     * Parameters are set by the constructor
     * @param active active player
     * @param islands list of islands
     * @param studentsOnCard list of students on the card
     */

    public AskMoveStudentFromCardToIslandMessage(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard) {
        super(active, MessageType.MOVING_ONE_STUDENT_FROM_CARD);
        this.islands = islands;
        this.studentsOnCard = studentsOnCard;
    }

    /**
     * @return the list of students on the card
     */


    public List<ColorPawns> getStudentsOnCard(){
        return this.studentsOnCard;
    }

    /**
     * @return the list of islands
     */

    public List<IslandModel> getIslands() {
        return islands;
    }

    @Override
    public String toString() {
        return "AskMoveOneStudentFromCardToIsland{" +
                "player=" + getNickname() +
                ", islands=" + islands +
                ", studentsOnCard=" + studentsOnCard +
                '}';
    }
}
