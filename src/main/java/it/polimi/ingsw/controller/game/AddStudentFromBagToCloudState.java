package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.game.PhaseGame;

//2
public class AddStudentFromBagToCloudState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public AddStudentFromBagToCloudState(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.ADD_STUDENT_CLOUD);
    }
}

