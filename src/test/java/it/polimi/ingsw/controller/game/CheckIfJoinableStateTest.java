package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckIfJoinableStateTest {


    @Test
    void testJoinIsland(){

        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
        IslandModel island1 = new IslandModel(Boolean.FALSE, ColorPawns.GREEN);
        island1.setTowerColor(ColorTower.GREY);
        IslandModel island2 = new IslandModel(Boolean.FALSE, Arrays.asList(ColorPawns.RED,ColorPawns.RED));
        island2.setTowerColor(ColorTower.BLACK);
        IslandModel island3 = new IslandModel(Boolean.FALSE, Arrays.asList(ColorPawns.BLUE,ColorPawns.BLUE, ColorPawns.YELLOW, ColorPawns.GREEN));
        island3.setTowerColor(ColorTower.BLACK);

        IslandModel island4 = new IslandModel(Boolean.FALSE, Arrays.asList(ColorPawns.PINK,ColorPawns.GREEN, ColorPawns.YELLOW, ColorPawns.PINK));
        islandList.add(island1);
        islandList.add(island2);
        islandList.add(island3);
        islandList.add(island4);
        testGame.setIslands(islandList);
        CheckIfJoinableState tester = new CheckIfJoinableState(testGame, island2);

        tester.joinIsland(island3, 1);

        assertEquals(ColorPawns.GREEN, testGame.getIslandsModel().get(0).getStudents().get(0));
        assertEquals(Arrays.asList(ColorPawns.RED,ColorPawns.RED, ColorPawns.BLUE,ColorPawns.BLUE, ColorPawns.YELLOW, ColorPawns.GREEN),testGame.getIslandsModel().get(1).getStudents() );

        assertEquals(Arrays.asList(ColorPawns.PINK,ColorPawns.GREEN, ColorPawns.YELLOW, ColorPawns.PINK), testGame.getIslandsModel().get(2).getStudents());
    }


    @Test
    void checkJoinableIslandIsRight(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
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
        assertEquals(ColorDirectionAdjacentIsland.RIGHT, testGame.getAdjacentSameColor(island2));
    }

    @Test
    void checkJoinableIslandIsLeft(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
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
        assertEquals(ColorDirectionAdjacentIsland.LEFT, testGame.getAdjacentSameColor(island2));
    }

    @Test
    void checkJoinableIslandIsBoth(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
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
        assertEquals(ColorDirectionAdjacentIsland.BOTH, testGame.getAdjacentSameColor(island2));
    }

    @Test
    void checkJoinableIslandIsLeftOnFirstPosition(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
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
        assertEquals(ColorDirectionAdjacentIsland.LEFT, testGame.getAdjacentSameColor(islandList.get(0)));
    }

    @Test
    void checkJoinableIslandIsRightOnLastPosition(){
        GameModel testGame = GameModel.getInstance();
        List<IslandModel> islandList = new ArrayList<>();
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
        assertEquals(ColorDirectionAdjacentIsland.RIGHT, testGame.getAdjacentSameColor(islandList.get(11)));
    }

}