package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.player.PlayerModel;

//4.1
public class StudentToIslandInitialState implements PlayerState {
    public PlayerModel playerModel;

    public StudentToIslandInitialState(PlayerModel playerModel){
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
