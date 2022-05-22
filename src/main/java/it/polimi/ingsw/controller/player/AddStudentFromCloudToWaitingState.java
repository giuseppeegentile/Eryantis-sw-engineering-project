package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.network.message.AddStudentFromCloudToEntranceMessage;
import it.polimi.ingsw.network.message.Message;

//last state
public class AddStudentFromCloudToWaitingState implements PlayerState {
    public PlayerModel playerModel;

    /**
     * Sets the player's state into choosing a cloud from which take a student
     * @param playerModel the model of the current player
     */

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
        this.playerModel.setStudentInEntrance(choosenCloudByPlayer.getStudents());
        choosenCloudByPlayer.cleanStudent();
    }

    /**
     * Checks if there are students left to move from a cloud to the player's board's entrance
     * @param receivedMessage Message received
     * @return false if there aren't any students left to move
     */
    //****************da testare
    public boolean moveStudentFromCloudToWaiting(Message receivedMessage){
        int cloudIndex = ((AddStudentFromCloudToEntranceMessage)receivedMessage).getCloudIndex();
        CloudModel chosenCloudByPlayer = GameModel.getInstance().getCloudsModel().get(cloudIndex);
        if(chosenCloudByPlayer.getStudents().size()!=0) {
            moveStudentFromCloudToWaiting(chosenCloudByPlayer);
            return true;
        }else
            return false;
    }


}
