package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.List;

//6
//sostituisce IslandController, fanno le stesse cose
//island conquered
public class CheckIslandState implements GameState {
    private final GameModel gameModel;
    private final IslandModel islandModel;

    /**
     * Constructor for the CheckIfConqueredState: initializes the gameModel
     * @param gameModel The current gameModel
     * @param islandModel The island to check
     */
    public CheckIslandState(GameModel gameModel, IslandModel islandModel){
        this.gameModel = gameModel;
        this.islandModel = islandModel;
        this.gameModel.setGameState(PhaseGame.CHECK_ISLAND);
    }

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public void onMessageReceived(Message receivedMessage) {
        VirtualView virtualView = gameModel.getVirtualViewMap().get(receivedMessage.getNickname());
    }

    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }

    public void addStudents(List<ColorPawns> studentsToAdd){
        islandModel.getStudents().addAll(studentsToAdd);
    }

    /**
     * Sets the tower color of the island to that of the player who conquered it
     * @param colorTower The tower color of the conquering player
     */
    public void moveTowerToIsland(ColorTower colorTower){
        this.islandModel.setTowerColor(colorTower);
    }

    /**
     * Sets the tower color of the island to the null value
     */
    public void removeTowerFromIsland(){
        this.islandModel.setTowerColor(ColorTower.NULL);
    }



}
