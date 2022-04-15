package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

//6
public class CheckIfConqueredState implements GameState {
    private final GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public CheckIfConqueredState(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void moveTowerToIsland(ColorTower colorTower, IslandModel islandModel){
        islandModel.setTowerColor(colorTower);
    }

    public void removeTowerFromIsland(IslandModel islandModel){
        islandModel.setTowerColor(ColorTower.NULL);
    }



}
