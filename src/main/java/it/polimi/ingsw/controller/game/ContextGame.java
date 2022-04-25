package it.polimi.ingsw.controller.game;

public class ContextGame {

    private GameState state;

    /**
     * Constructor for ContextGame: initializes state to the null value
     */
    public ContextGame(){
        this.state = null;
    }

    /**
     *
     * @return The current game state
     */
    public GameState getState() {
        return state;
    }

    /**
     *
     * @param state The next game state
     */
    public void setState(GameState state) {
        this.state = state;
    }
}
