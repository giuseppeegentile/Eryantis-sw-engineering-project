package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

//5
public class MoveMotherNatureState implements PlayerState {
    public PlayerModel playerModel;

    public MoveMotherNatureState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_MOTHER_NATURE);
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
