package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.ColorPawns.*;

public interface PlayerState {

     void addCoins();

     PlayerModel getPlayerModel();

}
