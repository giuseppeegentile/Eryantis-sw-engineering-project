package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentFromCloudToWaitingStateTest {

    @Test
    void moveStudentFromCloudToWaitingTest(){
        CloudModel cloud = new CloudModel(4);
        List<ColorPawns> students = new ArrayList<>(Arrays.asList(ColorPawns.GREEN, ColorPawns.RED,ColorPawns.GREEN, ColorPawns.BLUE));
        PlayerModel player = new PlayerModel("prova");

        cloud.setStudents(students);
        AddStudentFromCloudToWaitingState currState = new AddStudentFromCloudToWaitingState(player);

        currState.moveStudentFromCloudToWaiting(cloud);

        assertEquals(4,player.getStudentInEntrance().size());
        assertEquals(ColorPawns.GREEN,player.getStudentInEntrance().get(0));
        assertEquals(ColorPawns.RED,player.getStudentInEntrance().get(1));
        assertEquals(ColorPawns.GREEN,player.getStudentInEntrance().get(2));
        assertEquals(ColorPawns.BLUE,player.getStudentInEntrance().get(3));

    }
}