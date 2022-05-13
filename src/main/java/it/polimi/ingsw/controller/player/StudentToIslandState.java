package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.StudentToIslandMessage;

import java.util.List;

//4.1
public class StudentToIslandState implements PlayerState {
    private final PlayerModel playerModel;
    /**
     * Constructor for StudentToIslandState: sets the player state to MOVE_STUDENT
     * @param activePlayer The player who is moving the student
     */
    public StudentToIslandState(PlayerModel activePlayer){
        this.playerModel = activePlayer;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT_TO_ISLAND);

    }
    /**
     * Moves the students from the entrance to the island
     * @param students The students to place on the island
     * @param islandModel The island where the students have to be placed
     */
    public void moveStudentToIsland(List<ColorPawns> students, IslandModel islandModel){
        islandModel.addStudent(students);
        this.playerModel.removeStudentFromEntrance(students);
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



}
