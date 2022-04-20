package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentToHallStateTest {

    @Test
    void moveStudentToHallTest()  {
        PlayerModel player1 = new PlayerModel("player1");
        player1.addProf(ColorPawns.BLUE);
        player1.setStudentInEntrance(Arrays.asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.RED, ColorPawns.GREEN));
        StudentToHallState state = new StudentToHallState(player1);

        state.moveStudentToHall(ColorPawns.RED, GameMode.PRINCIPIANTE);
        assertEquals(Arrays.asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.GREEN), player1.getStudentInEntrance());
        state.moveStudentToHall(ColorPawns.BLUE, GameMode.PRINCIPIANTE);
        assertEquals(Arrays.asList(ColorPawns.BLUE, ColorPawns.GREEN), player1.getStudentInEntrance());
        state.moveStudentToHall(ColorPawns.BLUE, GameMode.PRINCIPIANTE);
        assertEquals(List.of(ColorPawns.GREEN), player1.getStudentInEntrance());
        state.moveStudentToHall(ColorPawns.GREEN, GameMode.PRINCIPIANTE);
        assertEquals(List.of(), player1.getStudentInEntrance());
    }

    @Test
    void assignProf() {

        PlayerModel player1 = new PlayerModel("player1");
        player1.addProf(ColorPawns.BLUE);

        PlayerModel player2 = new PlayerModel("player2");
        //player2.addProf(ColorPawns.GREEN);

        PlayerModel player3 = new PlayerModel("player3");
        player3.addProf(ColorPawns.GREEN);

        StudentToHallState state = new StudentToHallState(player1);

        state.assignProfToPlayer(Arrays.asList(player1, player2, player3), ColorPawns.PINK);
        assertEquals(ColorPawns.BLUE, player1.getProfs().get(0));
        assertEquals(ColorPawns.PINK, player1.getProfs().get(1));


        StudentToHallState state2 = new StudentToHallState(player2);
        state2.assignProfToPlayer(Arrays.asList(player1, player2, player3), ColorPawns.GREEN);
        assertEquals(ColorPawns.GREEN, player2.getProfs().get(0));
        assertEquals(List.of(), player3.getProfs());

    }
}