package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.controller.game.GameState;

public class ContextPlayer {


    private PlayerState state;

    public ContextPlayer(){
        this.state = null;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}
