package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.PlayCardAssistantState;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;

public class ContextGame {

    private GameState state;
    private PhaseGame phase;

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
