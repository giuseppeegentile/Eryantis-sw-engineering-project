package it.polimi.ingsw.controller.game;

//1

import it.polimi.ingsw.model.game.GameModel;

//SINGLETON
public class DecideFirstPlayerState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public DecideFirstPlayerState(GameModel gameModel){
        this.gameModel = gameModel;
    }
}
