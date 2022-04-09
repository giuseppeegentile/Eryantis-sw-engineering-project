package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.IslandModel;

//6
public class CheckIfConqueredState extends GameControllerState {

    public CheckIfConqueredState(GameModel gameModel) {
        super(gameModel);
    }

    private void moveTowerToIsland(ColorTower colorTower, IslandModel islandModel){
        islandModel.setTowerColor(colorTower);
    }
}
