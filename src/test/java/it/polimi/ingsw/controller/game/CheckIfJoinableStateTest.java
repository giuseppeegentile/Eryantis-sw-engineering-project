package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckIfJoinableStateTest {

    @Test
    void checkJoinableIslandIsRight(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<IslandModel>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.GREY);
        IslandModel island2 = new IslandModel(Boolean.FALSE, ColorPawns.RED);
        island2.setTowerColor(ColorTower.BLACK);
        IslandModel island3 = new IslandModel(Boolean.FALSE, ColorPawns.BLUE);
        island3.setTowerColor(ColorTower.BLACK);
        islandList.add(island1);
        islandList.add(island2);
        islandList.add(island3);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, island2);
        assertEquals(ColorDirectionAdjacentIsland.RIGHT, tester.getAdjacentSameColor());
    }

    @Test
    void checkJoinableIslandIsLeft(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<IslandModel>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.BLACK);
        IslandModel island2 = new IslandModel(Boolean.FALSE, ColorPawns.RED);
        island2.setTowerColor(ColorTower.BLACK);
        IslandModel island3 = new IslandModel(Boolean.FALSE, ColorPawns.BLUE);
        island3.setTowerColor(ColorTower.GREY);
        islandList.add(island1);
        islandList.add(island2);
        islandList.add(island3);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, island2);
        assertEquals(ColorDirectionAdjacentIsland.LEFT, tester.getAdjacentSameColor());
    }

    @Test
    void checkJoinableIslandIsBoth(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<IslandModel>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.BLACK);
        IslandModel island2 = new IslandModel(Boolean.FALSE, ColorPawns.RED);
        island2.setTowerColor(ColorTower.BLACK);
        IslandModel island3 = new IslandModel(Boolean.FALSE, ColorPawns.BLUE);
        island3.setTowerColor(ColorTower.BLACK);
        islandList.add(island1);
        islandList.add(island2);
        islandList.add(island3);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, island2);
        assertEquals(ColorDirectionAdjacentIsland.BOTH, tester.getAdjacentSameColor());
    }

    @Test
    void checkJoinableIslandIsLeftOnFirstPosition(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<IslandModel>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.BLACK);
        islandList.add(island1);
        for(int i=0; i<10; i++){
            islandList.add(new IslandModel(Boolean.FALSE, ColorPawns.BLUE));
        }
        IslandModel island2 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island2.setTowerColor(ColorTower.BLACK);
        islandList.add(island2);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, islandList.get(0));
        assertEquals(ColorDirectionAdjacentIsland.LEFT, tester.getAdjacentSameColor());
    }

    @Test
    void checkJoinableIslandIsRightOnLastPosition(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<IslandModel>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.BLACK);
        islandList.add(island1);
        for(int i=0; i<10; i++){
            islandList.add(new IslandModel(Boolean.FALSE, ColorPawns.BLUE));
        }
        IslandModel island2 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island2.setTowerColor(ColorTower.BLACK);
        islandList.add(island2);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, islandList.get(11));
        assertEquals(ColorDirectionAdjacentIsland.RIGHT, tester.getAdjacentSameColor());
    }

}