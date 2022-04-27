package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;

//last state
public class AddStudentFromCloudToWaitingState implements PlayerState {
    public PlayerModel playerModel;

    public AddStudentFromCloudToWaitingState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.CHOOSE_CLOUD_PICK_STUDENT);
    }

    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    /**
     * Moves the students from a cloud chosen by the player to his entrance
     * @param choosenCloudByPlayer The cloud from which the player takes the students
     */
    public void moveStudentFromCloudToWaiting(CloudModel choosenCloudByPlayer){
        this.playerModel.setStudentInEntrance(choosenCloudByPlayer.getStudent());
        choosenCloudByPlayer.cleanStudent();
    }
}
