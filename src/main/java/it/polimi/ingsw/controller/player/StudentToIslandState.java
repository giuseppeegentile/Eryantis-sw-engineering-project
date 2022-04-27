package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;

//4.1
public class StudentToIslandState implements PlayerState {
    private final PlayerModel playerModel;
    /**
     * Constructor for StudentToIslandState: sets the player state to MOVE_STUDENT
     * @param playerModel The player who is moving the student
     */
    public StudentToIslandState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT_TO_ISLAND);
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }

    /**
     * Moves the selected student from the entrance to the island
     * @param student The student to place on the island
     * @param islandModel The island where the student has to be placed
     */
    public void moveStudentToIsland(ColorPawns student, IslandModel islandModel){
        islandModel.addStudent(student);
        this.playerModel.removeStudentFromEntrance(student);
    }
}
