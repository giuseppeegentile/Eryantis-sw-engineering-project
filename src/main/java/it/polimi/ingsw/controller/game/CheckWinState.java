package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckWinState implements GameState {

    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public void onMessageReceived(Message receivedMessage) {
        VirtualView virtualView = gameModel.getVirtualViewMap().get(receivedMessage.getNickname());
    }


    public void setGameModel(GameModel gameModel){
        this.gameModel = gameModel;
    }

    CheckWinState(GameModel gameModel){
        this.gameModel = gameModel;
    }


}
