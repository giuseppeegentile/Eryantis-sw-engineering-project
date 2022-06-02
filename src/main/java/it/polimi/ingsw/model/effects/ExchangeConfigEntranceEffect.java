package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;

//7
public class ExchangeConfigEntranceEffect extends InitialConfigEffect implements Serializable {

    private static final long serialVersionUID = -8153829027755488618L;
    private List<ColorPawns> studentFromHall;
    private List<ColorPawns> studentFromCard;

    public void choose(List<ColorPawns> studentFromCard, List<ColorPawns> studentFromHall){
        this.studentFromCard = studentFromCard;
        this.studentFromHall = studentFromHall;
    }

    public ExchangeConfigEntranceEffect(List<ColorPawns> students) {
        super(students, 1, 6);
    }

    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(studentFromCard);
        playerModel.removeStudentFromHall(studentFromHall);

        this.getStudents().addAll(studentFromHall);
        this.getStudents().removeAll(studentFromCard);


        setCostForEffect(getCostForEffect()+1);
    }

    @Override
    public int getCoinsForEffect() {
        return getCostForEffect();
    }

    public String getDescription(){
        return super.getDescription() + " You can take up to 3 students from this card and exchange them with students on your hall.";
    }

}
