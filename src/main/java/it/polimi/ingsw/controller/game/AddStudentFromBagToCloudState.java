package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.game.PhaseGame;

//2
public class AddStudentFromBagToCloudState implements GameState {
    private GameModel gameModel;

    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * Change the state of the game with the one that add students from bag to clouds
     * @param gameModel The current gameModel
     */
    public AddStudentFromBagToCloudState(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.ADD_STUDENT_CLOUD);
    }

    /**
     * Adds students taken from the bag to the clouds. The number depends on the game mode
     */
    public void moveStudentFromBagToClouds(){
        int numStudentToMove = 3; //caso base: gioco a 2 o 4 devo spostare 3 studenti dal sacchetto all'isola
        if(this.gameModel.getPlayersNumber() == 3) //con 3 giocatori ne sposto 4
            numStudentToMove = 4;

        int finalNumStudentToMove = numStudentToMove;
        this.gameModel.getCloudsModel().forEach(c ->{
            int bagSize = this.gameModel.getBag().size();
            c.setStudents(this.gameModel.getBag().subList(bagSize - finalNumStudentToMove - 1, bagSize - 1)); // prendo dalla bag gli ultimi 3 studenti
            this.gameModel.getBag().subList(bagSize - finalNumStudentToMove - 1, bagSize - 1).clear(); //rimuove gli studenti appena spostati
        });
    }
}

