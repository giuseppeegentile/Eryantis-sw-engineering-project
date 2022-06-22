package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//11
public class AddToHallEffect extends InitialConfigEffect implements Serializable {
    private static final long serialVersionUID = -5901051516602737452L;
    private ColorPawns studentChosen;

    /**
     * Adds a student to the player's hall
     */

    public AddToHallEffect(List<ColorPawns> students) {
        super(students, 2, 4);
    }

    /**
     * Gets the student chosen thanks to the card's effect
     * @param studentChosen student chosen by the player
     */

    public void choose(ColorPawns studentChosen) {
        this.studentChosen = studentChosen;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        playerModel.addStudentsHall(List.of(studentChosen));
        this.students.remove(studentChosen);
        getFromBag();
    }

    /**
     * Gets the description of the character card
     */
    public String getDescription(){
        return super.getDescription() + "Take 1 student from this card and place it in your hall. Draw then a new student and place it on this card.";
    }
}

