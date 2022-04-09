package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.GameModel;

//2
public class AddStudentFromBagToCloudState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public AddStudentFromBagToCloudState(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}

