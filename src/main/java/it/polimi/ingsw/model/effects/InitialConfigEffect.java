package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InitialConfigEffect implements Effect, Serializable {
    private static final long serialVersionUID = 1760436139614445487L;
    private int costForEffect;
    final List<ColorPawns> students;
    private final int numStudents;

    /**
     * Constructor for the character card tha has "change initial configuration" as effect
     * @param students it's the list of the students
     * @param costForEffect it's the cost of the card
     * @param numStudents it's the number of students
     */

    public InitialConfigEffect(List<ColorPawns>  students, int costForEffect, int numStudents){
        this.students = students;
        this.costForEffect = costForEffect;
        this.numStudents = numStudents;
    }


    @Override
    public void enable(PlayerModel playerModel) {

    }

    /**
     * Gets students from the bag
     */

    void getFromBag(){
        ColorPawns stud = GameModel.getInstance().getBag().get(0);
        this.students.add(stud);
        GameModel.getInstance().getBag().remove(0);
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public void incrementCost() {
        this.costForEffect++;
        System.out.println(this.costForEffect);
    }

    @Override
    public String getDescription() {
        return "At the start of the match, take " + numStudents + " students and place them on this card.\n" +
                "                                  EFFECT: ";
    }

    /**
     * @return the number of students
     */
    public int getNumStudents() {
        return numStudents;
    }

    /**
     * @return the list of students
     */
    public List<ColorPawns> getStudents() {
        return new ArrayList<>(this.students);
    }

    /**
     * @return the card's effect cost in number of money
     */

    public int getCostForEffect() {
        return costForEffect;
    }

    /**
     * Constructor to set the card effect cost
     * @param costForEffect is the cost for the activation of the effect
     */

    public void setCostForEffect(int costForEffect){
        this.costForEffect = costForEffect;
    }
}

