package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.player.PlayerModel;

//5
public class MoveMotherNatureInitialState implements PlayerState {
    public PlayerModel playerModel;

    public MoveMotherNatureInitialState(PlayerModel playerModel){
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
