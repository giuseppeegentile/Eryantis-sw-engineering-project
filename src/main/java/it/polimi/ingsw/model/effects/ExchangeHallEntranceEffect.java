package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;
//10
public class ExchangeHallEntranceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 8204489708195898922L;
    private int costForEffect;
    private int moneyOnCard = 0;
    private List<ColorPawns> studentFromEntrance;
    private List<ColorPawns> studentFromHall;

    /**
     * Constructor for the character card tha has "exchange the hall configuration whit entrance's" as effect:
     * costForEffect is set to 1
     */

    public ExchangeHallEntranceEffect(){
        this.costForEffect = 1;
    }

    /**
     * Constructor made to initialize the parameters chosen by the player
     * @param studentFromEntrance students chosen from the entrance
     * @param studentFromHall students chosen from the hall
     */

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
        moneyOnCard++;
    }

    @Override
    public int getMoneyOnCard() {
        return moneyOnCard;
    }

    /**
     * @return the list of student chosen from entrance
     */
    public List<ColorPawns> getStudentFromEntrance() {
        return studentFromEntrance;
    }

    /**
     * @return the list of students chosen from hall
     */

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
