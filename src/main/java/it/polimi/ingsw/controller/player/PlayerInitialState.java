package it.polimi.ingsw.controller.player;

//stessa cosa del GameControllerState
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.colors.ColorPawns.*;

public class PlayerInitialState implements PlayerState {
    private final PlayerModel playerModel;
    private HashMap<ColorPawns, Integer> hall = new HashMap<>();

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

    //dato un'array di studenti crea la mappa della hall, per la prima volta imposta la mappa
    /**
     * Creates the map with the students for the player's hall. In expert mode adds coins tho the player if there is a multiple of 3 number of students of the same color
     * @param studentToAddToHall The list of students to add to the player's hall
     * @param gameMode The current game mode
     */
    private void setInitialStudentsHall(List<ColorPawns> studentToAddToHall, GameMode gameMode) {
        //conta le occorrenze per ogni studente di un colore.
        studentToAddToHall.forEach(s -> {
            if (hall.get(s) + 1 % 3 == 0 && gameMode == GameMode.ESPERTO) {//se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
                addCoins();
            }
            hall.put(s, hall.get(s) + 1);
        });

        //avrò una cosa del genere:
        //                GREEN, 5,
        //                RED, 3,
        //                YELLOW, 0,
        //                PINK, 1,
        //                BLUE, 2
        this.playerModel.setStudentHall(hall);
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

