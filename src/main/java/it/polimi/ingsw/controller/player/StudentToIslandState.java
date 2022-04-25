package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

//4.1
public class StudentToIslandState implements PlayerState {
    private final PlayerModel playerModel;

    /**
     * Constructor for StudentToIslandState: sets the player state to MOVE_STUDENT
     * @param playerModel The player who is moving the student
     */
    public StudentToIslandState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT);
    }

    @Override
    public void addCoins() {

    }

    @Override
    public void setCoins() {

    }

    @Override
    public void decrementCoins(int coinsUsed) {

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
