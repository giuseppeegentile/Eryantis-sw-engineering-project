package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

//4.0
public class StudentToHallState implements PlayerState {
    private PlayerModel playerModel;

    public StudentToHallState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT);
    }

    @Override
    public void addCoins() {

    }

    @Override
    public void setCoins() {

    }

    @Override
    public void decrementCoins(int coinsUsed) {

    }

    //sposta il singolo studente nella hall
    public void moveStudentToHall(ColorPawns student, GameMode gameMode){
        //conta le occorrenze per ogni studente di un colore
        if(this.playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && gameMode == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
            this.addCoins();

        this.playerModel.getStudentInHall().put(student, this.playerModel.getStudentInHall().get(student)+1);
        this.playerModel.removeStudentFromEntrance(student);
    }

    public void addProf(ColorPawns profToAdd){
        this.playerModel.addProf(profToAdd);
    }

    public void assignProfToPlayer(List<PlayerModel> playersModels, ColorPawns prof){
        AtomicBoolean i = new AtomicBoolean(false);
        playersModels.forEach(p ->{
            //se un altro giocatore ha già il prof
            if (!Objects.equals(this.playerModel.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                //lo tolgo a chi lo aveva prima
                p.removeProf(prof);
                playerModel.addProf(prof);
                i.set(true);
            }
        });
        if(!i.get())
            playerModel.addProf(prof);

    }
}
