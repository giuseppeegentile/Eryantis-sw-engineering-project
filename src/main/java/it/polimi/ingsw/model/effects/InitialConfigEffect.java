package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;

public class InitialConfigEffect implements Effect, Serializable {
    private static final long serialVersionUID = 1760436139614445487L;
    private int costForEffect;
    final List<ColorPawns> studentsConfig;
    private int numStudents;

    public InitialConfigEffect(List<ColorPawns> studentsConfig, int costForEffect, int numStudents){
        this.studentsConfig = studentsConfig;
        this.costForEffect = costForEffect;
        this.numStudents = numStudents;
    }

    @Override
    public void enable(PlayerModel playerModel) {

    }

    void getFromBag(){
        studentsConfig.add(GameModel.getInstance().getBag().get(0));
        GameModel.getInstance().getBag().remove(0);
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return "At the start of the match, take " + numStudents + " students and place them on this card.\n" +
                "EFFECT: ";
    }

    public List<ColorPawns> getStudentsConfig() {
        return studentsConfig;
    }

    public int getCostForEffect() {
        return costForEffect;
    }

    public void setCostForEffect(int costForEffect){
        this.costForEffect = costForEffect;
    }

}
