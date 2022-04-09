package it.polimi.ingsw.controller.game;

public class ContextGame {

    private GameState state;

    public ContextGame(){
        this.state = null;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
