package it.polimi.ingsw.controller.player;

//stessa cosa del GameControllerState
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.colors.ColorPawns.*;

public class PlayerInitialState implements PlayerState {
    private final PlayerModel playerModel;
    private HashMap<ColorPawns, Integer> hall;

    /**
     * Constructor for PlayerInitialState
     * @param playerModel The player to be initialized
     */
    public PlayerInitialState(PlayerModel playerModel) {
        this.playerModel = playerModel;
        hall = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    /**
     *
     * @param colorTower The tower color assigned to the player
     * @param towerNumber The number of tower of the player. It depends on the number of player
     */
    public void setTower(ColorTower colorTower, int towerNumber) {
        this.playerModel.setTowers(colorTower, towerNumber);
    }

    /**
     * Adds a coin to the player
     */
    @Override
    public void addCoins() {
        this.playerModel.addCoins();
    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }
}

