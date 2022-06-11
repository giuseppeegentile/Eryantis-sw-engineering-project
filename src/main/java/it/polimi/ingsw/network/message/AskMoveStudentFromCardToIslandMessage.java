package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class AskMoveStudentFromCardToIslandMessage extends Message {

    private static final long serialVersionUID = -2436545940503369104L;
    private final List<IslandModel> islands;
    private final List<ColorPawns> studentsOnCard;

    public AskMoveStudentFromCardToIslandMessage(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard) {
        super(active, MessageType.MOVING_ONE_STUDENT_FROM_CARD);
        this.islands = islands;
        this.studentsOnCard = studentsOnCard;
    }

    public List<ColorPawns> getStudentsOnCard(){
        return this.studentsOnCard;
    }

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
