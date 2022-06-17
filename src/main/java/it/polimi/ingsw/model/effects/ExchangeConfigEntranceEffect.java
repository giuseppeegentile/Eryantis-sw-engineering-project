package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;

//7
public class ExchangeConfigEntranceEffect extends InitialConfigEffect implements Serializable {

    private static final long serialVersionUID = -8153829027755488618L;
    private List<ColorPawns> studentFromEntrance;
    private List<ColorPawns> studentFromCard;

    public void choose(List<ColorPawns> studentFromCard, List<ColorPawns> studentFromEntrance){
        this.studentFromCard = studentFromCard;
        this.studentFromEntrance = studentFromEntrance;
    }

    public ExchangeConfigEntranceEffect(List<ColorPawns> students) {
        super(students, 1, 6);
    }

    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.getStudentInEntrance().addAll(studentFromCard);
        playerModel.getStudentInEntrance().removeAll(studentFromEntrance);

        students.addAll(studentFromEntrance);
        for(ColorPawns c : studentFromCard)
            students.remove(c);
    }


    @Override
    public int getCoinsForEffect() {
        return getCostForEffect();
    }

    public List<ColorPawns> getStudentFromCard() {
        return studentFromCard;
    }

    public List<ColorPawns> getStudentFromEntrance() {
        return studentFromEntrance;
    }

    public String getDescription(){
        return super.getDescription() + "you can take up to 3 students from this card and exchange them with students in your entrance.";
    }

}
