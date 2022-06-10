package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//10
public class ExchangeHallEntranceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 8204489708195898922L;
    private int costForEffect;
    private List<ColorPawns> studentFromEntrance;
    private List<ColorPawns> studentFromHall;

    public ExchangeHallEntranceEffect(){
        this.costForEffect = 1;
    }

    public void choose(List<ColorPawns> studentFromEntrance, List<ColorPawns> studentFromHall){
        this.studentFromEntrance = studentFromEntrance;
        this.studentFromHall = studentFromHall;
    }


    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(studentFromEntrance);
        playerModel.addStudentsEntrance(studentFromHall);

        playerModel.removeStudentFromEntrance(studentFromEntrance);
        playerModel.removeStudentFromHall(studentFromHall);
    }
    @Override
    public void incrementCost() {
        this.costForEffect++;
        System.out.println(this.costForEffect);
    }

    public List<ColorPawns> getStudentFromEntrance() {
        return studentFromEntrance;
    }

    public List<ColorPawns> getStudentFromHall() {
        return studentFromHall;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }


    @Override
    public String getDescription() {
        return "EFFECT: you can exchange up to 2 students present in your entrance and in your hall.";
    }
}
