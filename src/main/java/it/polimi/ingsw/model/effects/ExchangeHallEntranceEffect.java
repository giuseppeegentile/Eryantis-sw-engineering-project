package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//10
public class ExchangeHallEntranceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 8204489708195898922L;
    int costForEffect = 1;
    private List<ColorPawns> studentFromEntrance;
    private List<ColorPawns> studentFromHall;

    public void chose(List<ColorPawns> studentFromEntrance, List<ColorPawns> studentFromHall){
        this.studentFromEntrance = studentFromEntrance;
        this.studentFromHall = studentFromHall;
    }


    @Override
    public void enable(PlayerModel playerModel) {
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

    @Override
    public String getDescription() {
        return null;
    }
}
