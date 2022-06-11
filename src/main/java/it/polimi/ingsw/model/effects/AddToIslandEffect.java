package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//1
public class AddToIslandEffect extends InitialConfigEffect implements Serializable {
    private static final long serialVersionUID = -872892232191009661L;
    private ColorPawns studentChosen;
    private IslandModel islandChosen;

    public AddToIslandEffect(List<ColorPawns> students) {
        super(students, 1, 4);
    }

    public void choose(ColorPawns indexStudent, int indexIsland) {
        this.studentChosen = indexStudent;
        this.islandChosen = GameModel.getInstance().getIslandsModel().get(indexIsland);
    }

    @Override
    public void enable(PlayerModel playerModel) {
        islandChosen.addStudent(studentChosen);
        getFromBag();
    }

    public ColorPawns getStudentChosen() {
        return studentChosen;
    }

    public IslandModel getIslandChosen() {
        return islandChosen;
    }


    public String getDescription(){
        return super.getDescription() + " take 1 student from this card and place it on an island you chose. Then, draw a student from the bag and place it on this card.";
    }
}

