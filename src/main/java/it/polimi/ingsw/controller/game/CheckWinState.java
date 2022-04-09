package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckWinState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public CheckWinState(GameModel gameModel){
        this.gameModel = gameModel;
    }


    //da chiamare quando rimangono solo 3 isole unificate
    //alla fine del round in cui viene pescato l'ultimo studente o giocata l'ultima carta
    private PlayerModel checkWin() {
        PlayerModel winner = null;
        List<PlayerModel> playersModels = getGameModel().getPlayersModel();
        //corrispondenza indice(studente) - valore (numero torre)
        List<Integer> towersNumber = new ArrayList<>(playersModels.size());
        //corrispondenza indice(studente) - valore (numero prof)
        List<Integer> profNumber = new ArrayList<>(playersModels.size());
        playersModels.forEach(p -> {
            towersNumber.add(p.getTowerNumber());
            profNumber.add(p.getNumProfs());
        });
        byte count = 0; //numero di giocatori con stesso numero di torri
        int minNumTower, maxNumProf;
        int indexMinTower;
        minNumTower = Collections.min(towersNumber);
        indexMinTower = towersNumber.indexOf(minNumTower);
        int i = 0; // is the index Of Player With Same Tower Number (if any)
        for (i = 0; i < playersModels.size(); i++) {
            if (i!=indexMinTower && playersModels.get(i).getTowerNumber() == minNumTower) {
                count++;
                break; //esci dal ciclo, ci sono due giocatori con stesso numero di torri
            }
        }
        if (count != 0) { //se ci sono giocatori con stesso # di torri -> controlla numProf
            //caso in cui non giocatori hanno stesso numero di torri, e stesso numero di prof
            /*if (playersModels.get(i).getNumProfs() == playersModels.get(indexMinTower).getNumProfs()){
                return new PlayerModel("_NO_WINNER_CASE_",ColorTower.GREY,0);
            }*/
            //prende, tra i due a parità di torri, il giocatore con più prof
            return playersModels.get(i).getNumProfs() > playersModels.get(indexMinTower).getNumProfs() ?
                    playersModels.get(i) : playersModels.get(indexMinTower);
        } else {        //controlla torri -> restituisce quello con minimo numero di torri
            return playersModels.get(towersNumber.indexOf(minNumTower));

        }
    }
}
