package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

//7
public class ExchangeConfigEntranceEffect extends InitialConfigEffect{

    private List<ColorPawns> studentFromHall;
    private List<ColorPawns> studentFromCard;

    public void choose(List<ColorPawns> studentFromCard, List<ColorPawns> studentFromHall){
        this.studentFromCard = studentFromCard;
        this.studentFromHall = studentFromHall;
    }

    public ExchangeConfigEntranceEffect(List<ColorPawns> students) {
        super(students, 1);
    }
    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(studentFromCard);
        playerModel.removeStudentFromHall(studentFromHall);

        students.addAll(studentFromHall);
        students.removeAll(studentFromCard);


        setCostForEffect(getCostForEffect()+1);
    }

    @Override
    public int getCoinsForEffect() {
        return getCostForEffect();
    }
}
