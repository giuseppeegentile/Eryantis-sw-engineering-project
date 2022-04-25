package it.polimi.ingsw.controller.game;

//1

import it.polimi.ingsw.model.game.GameModel;

//SINGLETON
public class DecideFirstPlayerState implements GameState {
    private GameModel gameModel;

    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * Constructor for DecideFirstPlayerState
     * @param gameModel The current gameModel
     */
    public DecideFirstPlayerState(GameModel gameModel){
        this.gameModel = gameModel;
    }
}
