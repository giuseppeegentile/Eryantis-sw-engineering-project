package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.CloudModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentFromBagToCloudStateTest extends StartGameStateTest{

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