package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

//6
public class CheckIfConqueredState implements GameState {
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
     * Constructor for the CheckIfConqueredState: initializes the gameModel
      * @param gameModel The current gameModel
     */
    public CheckIfConqueredState(GameModel gameModel){
        this.gameModel = gameModel;
    }

    /**
     * Sets the tower color of the island to that of the player who conquered it
     * @param colorTower The tower color of the conquering player
     * @param islandModel
     */
    public void moveTowerToIsland(ColorTower colorTower, IslandModel islandModel){
        islandModel.setTowerColor(colorTower);
    }

    /**
     * Sets the tower color of the island to the null value
     * @param islandModel The island from which we have to remove the tower
     */
    public void removeTowerFromIsland(IslandModel islandModel){
        islandModel.setTowerColor(ColorTower.NULL);
    }



}
