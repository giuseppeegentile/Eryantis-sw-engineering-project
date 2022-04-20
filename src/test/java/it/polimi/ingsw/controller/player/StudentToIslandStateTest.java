package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.controller.game.StartGameState;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentToIslandStateTest {


    GameModel testGame = GameModel.getInstance();
    StartGameState ssg = new StartGameState(testGame);
    PlayerModel player1 = new PlayerModel("davide");
    void init(){
        List<PlayerModel> playersModels = new ArrayList<>();

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
    void moveStudentToIslandTest(){
        init();
        List<ColorPawns> studentOnIsland = new ArrayList<>(Arrays.asList(ColorPawns.BLUE,ColorPawns.BLUE,ColorPawns.RED,ColorPawns.BLUE, ColorPawns.GREEN));
        IslandModel islandModel = new IslandModel(false, studentOnIsland);

        StudentToIslandState studentToIsland = new StudentToIslandState(player1);

        studentToIsland.moveStudentToIsland(ColorPawns.PINK, islandModel);

        assertEquals(6, islandModel.getStudents().size());
        assertEquals(Arrays.asList(ColorPawns.BLUE,ColorPawns.BLUE,ColorPawns.RED,ColorPawns.BLUE, ColorPawns.GREEN, ColorPawns.PINK), islandModel.getStudents());
    }
}