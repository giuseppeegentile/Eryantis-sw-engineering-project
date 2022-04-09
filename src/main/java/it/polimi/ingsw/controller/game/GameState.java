package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public interface GameState {

    GameModel getGameModel();

}
