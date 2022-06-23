package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//11
public class AddToHallEffect extends InitialConfigEffect implements Serializable {
    private static final long serialVersionUID = -5901051516602737452L;
    private ColorPawns studentChosen;
    private GameController controller;
    /**
     * Constructor for the character card that has "add student from card to hall" as effect:costForEffect is set to
     * 2 and numStudents to 4
     */

    public AddToHallEffect(List<ColorPawns> students, GameController controller) {
        super(students, 2, 4);
        this.controller = controller;
    }

    /**
     * Constructor made to set the chosen the student to move
     * @param studentChosen student chosen by the player
     */

    public void choose(ColorPawns studentChosen) {
        this.studentChosen = studentChosen;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        //playerModel.addStudentsHall(List.of(studentChosen));
        controller.moveStudentToHall(playerModel, List.of(studentChosen), true);
        getFromBag();
    }



    /**
     * Gets the description of the character card
     */
    public String getDescription(){
        return super.getDescription() + "Take 1 student from this card and place it in your hall. Draw then a new student and place it on this card.";
    }
}

