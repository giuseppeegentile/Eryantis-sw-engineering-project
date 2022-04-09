package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;

//4.0
public class StudentToHallInitialState implements PlayerState {
    public PlayerModel playerModel;

    public StudentToHallInitialState(PlayerModel playerModel){
        this.playerModel = playerModel;
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
        if(playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && gameMode == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
            this.addCoins();

        playerModel.getStudentInHall().put(student, playerModel.getStudentInHall().get(student)+1);
    }
    public void addProf(ColorPawns profToAdd){
        this.playerModel.setProfs(profToAdd);
    }


/*
    ****************************************************************************************
    //DA SISTEMARE
    //controlla se un giocatore già possiede il prof, nel caso lo toglie e lo assegna al giocatore conquistatore
    public void assignProfToPlayer(PlayerModel playerConquerer, ColorPawns color){
        List<PlayerModel> playersModels = gameModel.getPlayersModel();
        playersModels.get(playersModels.indexOf(playerConquerer)).incrementNumProfs();

        for(int i=0; i<playersModels.size(); i++){
            if(playersModels.get(i).constains(color))
                playersModels.get(i).removeProf(playersModels.getProfs());
            playersModels.get(i).decrementNumProfs();
            gameModel.playersModels(gameModel.playersModels.indexOf(playerConquerer)).profs.add(color);
        }
    }
    */
}
