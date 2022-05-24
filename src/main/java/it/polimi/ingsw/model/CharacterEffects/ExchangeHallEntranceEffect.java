package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
//10
public class ExchangeHallEntranceEffect extends Effect{
    int costForEffect = 1;
    private final List<ColorPawns> studentFromEntrance;
    private final List<ColorPawns> studentFromHall;

    public ExchangeHallEntranceEffect(List<ColorPawns> studentFromEntrance, List<ColorPawns> studentFromHall){
        this.studentFromEntrance = studentFromEntrance;
        this.studentFromHall = studentFromHall;
    }

    @Override
    void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(studentFromEntrance);
        playerModel.addStudentsEntrance(studentFromHall);

        playerModel.removeStudentFromEntrance(studentFromEntrance);
        playerModel.removeStudentFromHall(studentFromHall);
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }
}
