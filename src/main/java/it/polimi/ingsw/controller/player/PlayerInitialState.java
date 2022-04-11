package it.polimi.ingsw.controller.player;

//stessa cosa del GameControllerState
import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.ColorPawns.*;

public class PlayerInitialState implements PlayerState {
    public PlayerModel playerModel;

    public PlayerInitialState(PlayerModel playerModel){
        this.playerModel = playerModel;
    }


    public void setTower(ColorTower colorTower, int towerNumber){
        this.playerModel.setTowers(colorTower, towerNumber);
    }

    //dato un'array di studenti crea la mappa della hall, per la prima volta imposta la mappa
    private void setInitialStudentsHall(List<ColorPawns> studentToAddToHall, GameMode gameMode){
        Map<ColorPawns, Integer> hall = Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        );
        //conta le occorrenze per ogni studente di un colore.
        studentToAddToHall.forEach (s -> {
            if(hall.get(s)+1 % 3 == 0 && gameMode == GameMode.ESPERTO) {//se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
                addCoins();
            }
            hall.put(s, hall.get(s)+1);
        });

        //avrò una cosa del genere:
        //                GREEN, 5,
        //                RED, 3,
        //                YELLOW, 0,
        //                PINK, 1,
        //                BLUE, 2
        this.playerModel.setStudentHall(hall);
    }

    public void addCoins(){
        this.playerModel.addCoins();
    }

    public void setCoins(){
        this.playerModel.setCoins();
    }

    public void decrementCoins(int coinsUsed){
        this.playerModel.removeCoins(coinsUsed);
    }
}