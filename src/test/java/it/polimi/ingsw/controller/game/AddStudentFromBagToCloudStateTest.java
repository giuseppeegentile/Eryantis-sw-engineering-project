package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentFromBagToCloudStateTest {

    @Test
    public void movingStudentsFromBagToCloud(){  //non funziona
        GameModel testGame = new GameModel();
        List<CloudModel> clouds = new ArrayList<CloudModel>();
        CloudModel testCloud1 = new CloudModel(3);
        CloudModel testCloud2 = new CloudModel(3);
        clouds.add(testCloud1);
        clouds.add(testCloud2);
        StartGameState startGameStateTest = new StartGameState(testGame);
        startGameStateTest.assignBag();
        testGame.setCloudsModel(clouds);
        AddStudentFromBagToCloudState addStudentTest = new AddStudentFromBagToCloudState(testGame);
        List<ColorPawns> bag1 = testGame.getBag().subList(120 - 3 - 1, 120 - 1);
        List<ColorPawns> bag2 = testGame.getBag().subList(120 - 6 - 1, 117 - 1);
        List<ColorPawns> bag3 = testGame.getCloudsModel().get(0).getStudent();
        List<ColorPawns> bag4 = testGame.getCloudsModel().get(1).getStudent();
        addStudentTest.moveStudentFromBagToClouds();
        testGame.getCloudsModel().get(0);
        assertEquals(bag1, bag3);
        assertEquals(bag2, bag4);
    }
}