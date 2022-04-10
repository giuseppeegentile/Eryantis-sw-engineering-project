package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.player.PlayerModel;

//last state
public class AddStudentFromCloudToWaitingState implements PlayerState {
    public PlayerModel playerModel;

    public AddStudentFromCloudToWaitingState(PlayerModel playerModel){
        this.playerModel = playerModel;
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
}
