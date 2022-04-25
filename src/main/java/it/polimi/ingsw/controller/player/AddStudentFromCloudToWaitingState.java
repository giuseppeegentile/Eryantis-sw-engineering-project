package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

//last state
public class AddStudentFromCloudToWaitingState implements PlayerState {
    public PlayerModel playerModel;

    public AddStudentFromCloudToWaitingState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.CHOOSE_CLOUD_PICK_STUDENT);
    }

    /**
     *
     */
    @Override
    public void addCoins() {

    }

    /**
     *
     */
    @Override
    public void setCoins() {

    }

    /**
     *
     * @param coinsUsed
     */
    @Override
    public void decrementCoins(int coinsUsed) {

    }

    /**
     * Moves the students from a cloud chosen by the player to his entrance
     * @param chosenCloudByPlayer The cloud from which the player takes the students
     */
    public void moveStudentFromCloudToWaiting(CloudModel chosenCloudByPlayer){
        this.playerModel.setStudentInEntrance(chosenCloudByPlayer.getStudent());
        chosenCloudByPlayer.cleanStudent();
    }
}
