package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.player.PlayerModel;

//last state
public class AddStudentFromCloudToWaitingInitialState implements PlayerState {
    public PlayerModel playerModel;

    public AddStudentFromCloudToWaitingInitialState(PlayerModel playerModel){
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
