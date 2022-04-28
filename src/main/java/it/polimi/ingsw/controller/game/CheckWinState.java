package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;



public class CheckWinState extends GameController implements GameState {

    private GameModel gameModel;

    CheckWinState(GameModel gameModel){
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.CHECK_WIN);
    }
    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public void onMessageReceived(Message receivedMessage) {
        VirtualView virtualView = gameModel.getVirtualViewMap().get(receivedMessage.getNickname());
    }
    /**
     *
     * @param gameModel The current gameModel
     */
    public void setGameModel(GameModel gameModel){
        this.gameModel = gameModel;
    }

    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }
}
