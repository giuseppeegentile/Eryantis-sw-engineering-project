package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
//1
public class AddToIslandEffect extends InitialConfigEffect{
    private ColorPawns studentChosen;
    private IslandModel islandChosen;
    public AddToIslandEffect(List<ColorPawns> students) {
        super(students, 1);
    }

    public void choose(int indexStudent, int indexIsland) {
        this.studentChosen = students.get(indexStudent);
        this.islandChosen = GameModel.getInstance().getIslandsModel().get(indexIsland);

    }

    @Override
    public void enable(PlayerModel playerModel) {
        islandChosen.addStudent(studentChosen);

        getFromBag();

        setCostForEffect(getCostForEffect()+1);
    }

    @Override
    public int getCoinsForEffect() {
        return getCostForEffect();
    }
}
