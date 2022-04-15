package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentFromBagToCloudStateTest {
    GameModel testGame = GameModel.getInstance();
    StartGameState ssg = new StartGameState(testGame);

    void init(){
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide");
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian");
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe");
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto");
        playersModels.add(player4);

        List<ColorTower> colorTowers = new ArrayList<>(Arrays.asList(ColorTower.BLACK, ColorTower.WHITE, ColorTower.BLACK, ColorTower.WHITE));
        GameMode princ = GameMode.PRINCIPIANTE;

        ssg.setInitialGameConfiguration(playersModels, colorTowers, princ);
    }

    @Test
    public void movingStudentsFromBagToCloud(){  //non funziona
        init();

        //ssg.assignBag();

        AddStudentFromBagToCloudState addStudentTest = new AddStudentFromBagToCloudState(testGame);

        List<ColorPawns> studentFromBag = testGame.getBag().subList(88, 92);
        List<ColorPawns> studentFromBag2 = testGame.getBag().subList(85, 89);
        addStudentTest.moveStudentFromBagToClouds();

        List<ColorPawns> studentOfFirstCloud = testGame.getCloudsModel().get(0).getStudents();

        List<ColorPawns> studentOfSecondCloud = testGame.getCloudsModel().get(1).getStudents();
        int numCloud= 0;
        for (CloudModel c: this.testGame.getCloudsModel()) {
            //System.out.println("Cloud_" + numCloud + " has: " + c.getStudents());
            numCloud++;
        }
        //System.out.println("Dalla bag " + studentFromBag);
        assertEquals(studentFromBag, studentOfFirstCloud);
        assertEquals(studentFromBag2,studentOfSecondCloud );
    }
}