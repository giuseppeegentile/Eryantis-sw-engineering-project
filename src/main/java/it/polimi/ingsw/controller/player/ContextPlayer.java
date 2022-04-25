package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.controller.game.GameState;

public class ContextPlayer {


    private PlayerState state;

    /**
     * Constructor for ContextPlayer: initializes state to the null value
     */
    public ContextPlayer(){
        this.state = null;
    }

    /**
     *
     * @return The current player state
     */
    public PlayerState getState() {
        return state;
    }

    /**
     *
     * @param state The next player state
     */
    public void setState(PlayerState state) {
        this.state = state;
    }
}
