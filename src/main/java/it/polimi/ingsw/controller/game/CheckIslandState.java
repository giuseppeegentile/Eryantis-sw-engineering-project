package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

//6
//sostituisce IslandController, fanno le stesse cose
//island conquered
public class CheckIslandState implements GameState {
    private final GameModel gameModel;
    private final IslandModel islandModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public CheckIslandState(GameModel gameModel, IslandModel islandModel){
        this.gameModel = gameModel;
        this.islandModel = islandModel;
    }

    public void addStudents(List<ColorPawns> studentsToAdd){
        islandModel.getStudents().addAll(studentsToAdd);
    }

    public void moveTowerToIsland(ColorTower colorTower){
        this.islandModel.setTowerColor(colorTower);
    }

    public void removeTowerFromIsland(){
        this.islandModel.setTowerColor(ColorTower.NULL);
    }



}
