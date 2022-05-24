package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
//11
public class AddToHallEffect extends InitialConfigEffect{
    private ColorPawns studentChosen;

    public AddToHallEffect(List<ColorPawns> students) {
        super(students, 2);
    }

    public void choose(int indexStudent) {
        this.studentChosen = students.get(indexStudent);

    }

    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(List.of(studentChosen));

        getFromBag();

        setCostForEffect(getCostForEffect()+1);
    }

    @Override
    public int getCoinsForEffect() {
        return getCostForEffect();
    }
}
