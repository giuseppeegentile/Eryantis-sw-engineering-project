package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.network.message.Message;

import java.util.List;

public interface GameState {

    GameModel getGameModel();
    void onMessageReceived(Message receivedMessage);
    PhaseGame getState();
}
