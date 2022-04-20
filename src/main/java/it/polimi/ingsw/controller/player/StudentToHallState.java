package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

import java.util.List;
import java.util.Objects;

//4.0
public class StudentToHallState implements PlayerState {
    private final PlayerModel playerModel;

    public StudentToHallState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT);
    }


    //sposta il singolo studente nella hall
    public void moveStudentToHall(ColorPawns student, GameMode gameMode) {
        //conta le occorrenze per ogni studente di un colore
        if(this.playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && gameMode == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
            this.addCoins();

        this.playerModel.getStudentInHall().put(student, this.playerModel.getStudentInHall().get(student)+1);
        this.playerModel.removeStudentFromEntrance(student);
    }

    public void assignProfToPlayer(List<PlayerModel> playersModels, ColorPawns prof){
        boolean alreadyHave = false;
        for (PlayerModel p : playersModels) {
            //se un altro giocatore ha già il prof
            if(p.getProfs() != null) {
                if (!Objects.equals(this.playerModel.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                    //lo tolgo a chi lo aveva prima
                    p.removeProf(prof);
                    playerModel.addProf(prof);
                    alreadyHave = true;
                }
            }
        }
        if(!alreadyHave)
            playerModel.addProf(prof);

    }


    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }

}
