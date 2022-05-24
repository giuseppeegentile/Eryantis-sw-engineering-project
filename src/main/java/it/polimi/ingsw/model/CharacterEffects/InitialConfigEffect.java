package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class InitialConfigEffect implements Effect{
    private int costForEffect;
    final List<ColorPawns> students;

    public InitialConfigEffect(List<ColorPawns>  students, int costForEffect){
        this.students = students;
        this.costForEffect = costForEffect;
    }
    @Override
    public void enable(PlayerModel playerModel) {

    }

    void getFromBag(){

        students.add(GameModel.getInstance().getBag().get(0));
        GameModel.getInstance().getBag().remove(0);
    }

    @Override
    public int getCoinsForEffect() {
        return 0;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }

    public int getCostForEffect() {
        return costForEffect;
    }

    public void setCostForEffect(int costForEffect){
        this.costForEffect = costForEffect;
    }
}
