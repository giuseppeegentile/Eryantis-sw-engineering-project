package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.PlayerModel;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.ColorPawns.*;

public class PlayerController {
    PlayerModel playerModel;

    public PlayerController(PlayerModel playerModel){
        this.playerModel = playerModel;
    }

    public void addProf(ColorPawns profToAdd){
        this.playerModel.setProfs(profToAdd);
    }

    public void setTower(ColorTower colorTower, int towerNumber){
        this.playerModel.setTowers(colorTower, towerNumber);
    }

    //dato un'array di studenti crea la mappa della hall, per la prima volta imposta la mappa
    public void setInitialStudentsHall(List<ColorPawns> studentToAddToHall, GameMode gameMode){
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

    //sposta il singolo studente nella hall
    public void moveStudentToHall(ColorPawns student, GameMode gameMode){
        //conta le occorrenze per ogni studente di un colore
        if(playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && gameMode == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
            addCoins();

        playerModel.getStudentInHall().put(student, playerModel.getStudentInHall().get(student)+1);
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
