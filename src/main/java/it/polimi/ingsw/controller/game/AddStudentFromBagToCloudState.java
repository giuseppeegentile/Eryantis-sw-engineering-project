package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

//2
public class AddStudentFromBagToCloudState extends GameController implements GameState {
    private final GameModel gameModel;

    /**
     * Change the state of the game with the one that add students from bag to clouds
     * @param gameModel The current gameModel
     */
    public AddStudentFromBagToCloudState(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.ADD_STUDENT_CLOUD);
    }

    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }



    /**
     * Adds students taken from the bag to the clouds. The number depends on the game mode
     */
    public void moveStudentFromBagToClouds(){
        int numStudentToMove = 3; //caso base: gioco a 2 o 4 devo spostare 3 studenti dal sacchetto all'isola
        if(this.gameModel.getPlayersNumber() == 3) //con 3 giocatori ne sposto 4
            numStudentToMove = 4;

        int bagSize = this.gameModel.getBag().size();
        int i = 0;
        for (CloudModel c :this.gameModel.getCloudsModel()) {

            c.setStudents(this.gameModel.getBag().subList(bagSize - numStudentToMove*(i+1) - 1, bagSize-numStudentToMove*i));// prendo dalla bag gli ultimi 3 studenti
            //this.gameModel.getBag().subList(bagSize - numStudentToMove - 1, bagSize - 1).clear(); //rimuove gli studenti appena spostati
            i++;
        }
        //RIMUOVERE QUI GLI ELEMENTI DALLA BAG, ALTRIMENTI CREA CASINI
        this.gameModel.setBag(this.gameModel.getBag().subList(0, this.gameModel.getBag().size() - this.gameModel.getPlayersNumber()*numStudentToMove));
    }
}

