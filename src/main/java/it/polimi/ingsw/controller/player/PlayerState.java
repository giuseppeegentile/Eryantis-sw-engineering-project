package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.model.player.PlayerModel;

public interface PlayerState {

     void addCoins();

     PlayerModel getPlayerModel();

     StatePlayer getState();

}
