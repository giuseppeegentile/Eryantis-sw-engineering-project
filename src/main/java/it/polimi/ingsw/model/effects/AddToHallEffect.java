package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
//11
public class AddToHallEffect extends InitialConfigEffect{
    private ColorPawns studentChosen;

    public AddToHallEffect(List<ColorPawns> students) {
        super(students, 2, 4);
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

    public String getDescription(){
        return super.getDescription() + " Take 1 student from this card and place it in your hall. Draw then a new student and place it on this card.";
    }
}