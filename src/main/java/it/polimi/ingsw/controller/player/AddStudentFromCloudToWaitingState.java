package it.polimi.ingsw.controller.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.network.message.AddStudentFromCloudToWaitingMessage;
import it.polimi.ingsw.network.message.Message;

import java.util.List;

//last state
public class AddStudentFromCloudToWaitingState implements PlayerState {
    public PlayerModel playerModel;

    public AddStudentFromCloudToWaitingState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.CHOOSE_CLOUD_PICK_STUDENT);
    }

    public AddStudentFromCloudToWaitingState(Message receivedMessage){
        this.playerModel = GameModel.getInstance().getPlayerByNickname(receivedMessage.getNickname());
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

    //****************da testare
    public boolean moveStudentFromCloudToWaiting(Message receivedMessage){
        int cloudIndex = ((AddStudentFromCloudToWaitingMessage)receivedMessage).getCloudIndex();
        CloudModel choosenCloudByPlayer = GameModel.getInstance().getCloudsModel().get(cloudIndex);
        if(choosenCloudByPlayer.getStudent().size()!=0) {
            moveStudentFromCloudToWaiting(choosenCloudByPlayer);
            return true;
        }else
            return false;
    }


}
