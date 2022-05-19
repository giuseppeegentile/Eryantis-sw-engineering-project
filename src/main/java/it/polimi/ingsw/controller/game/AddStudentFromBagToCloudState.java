package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;

//2
public class AddStudentFromBagToCloudState extends GameController implements GameState, Serializable {
    private static final long serialVersionUID = 2562163114025500822L;

    /**
     * Change the state of the game with the one that add students from bag to clouds
     */
    public AddStudentFromBagToCloudState() {
        GameModel.getInstance().setGameState(PhaseGame.ADD_STUDENT_CLOUD);
    }


    @Override
    public PhaseGame getState() {
        return GameModel.getInstance().getGameState();
    }


    /**
     * Adds students taken from the bag to the clouds. The number depends on the game mode
     */
    //da testare di nuovo
    public void moveStudentFromBagToClouds(){
        GameModel gameInstance = GameModel.getInstance();
        int numStudentToMove = 3; //caso base: gioco a 2 o 4 devo spostare 3 studenti dal sacchetto all'isola
        if(gameInstance.getPlayersNumber() == 3) //con 3 giocatori ne sposto 4
            numStudentToMove = 4;

        int bagSize = gameInstance.getBag().size();
        int i = 0;
        for (CloudModel c :gameInstance.getCloudsModel()) {

            c.setStudents(gameInstance.getBag().subList(bagSize - numStudentToMove*(i+1), bagSize-numStudentToMove*i));// prendo dalla bag gli ultimi 3 studenti
            //this.gameModel.getBag().subList(bagSize - numStudentToMove - 1, bagSize - 1).clear(); //rimuove gli studenti appena spostati

            i++;
        }
        //RIMUOVERE QUI GLI ELEMENTI DALLA BAG, ALTRIMENTI CREA CASINI
        gameInstance.setBag(gameInstance.getBag().subList(0, gameInstance.getBag().size() - gameInstance.getPlayersNumber()*numStudentToMove));
    }
}

