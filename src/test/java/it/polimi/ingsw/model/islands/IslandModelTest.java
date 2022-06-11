package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandModelTest {
    static GameModel testGame = GameModel.getInstance();

    @Test
    @BeforeAll
    static void init() {
        testGame.endGame();
        testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.WHITE);
        player3.setTowers(ColorTower.WHITE, 5);
        playersModels.add(player3);
        testGame.setPlayers(playersModels);
    }

    @Test
    void getInfluence() {
        List<ColorPawns> studentOnIsland = new ArrayList<>(Arrays.asList( ColorPawns.RED, ColorPawns.RED, ColorPawns.RED));
        IslandModel islandModel = new IslandModel(true, ColorPawns.BLUE);
        islandModel.addStudent(studentOnIsland);
        islandModel.setTowerColor(ColorTower.GREY);
        assertEquals(ColorTower.GREY, islandModel.getTowerColor());
        assertNull(islandModel.getInfluence(null, null, true).getNickname());

    }

    @Test
    void getInfluencePlayerWithAdditionalInfluence() {
        List<ColorPawns> studentOnIsland = new ArrayList<>(Arrays.asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.RED ));
        IslandModel islandModel = new IslandModel(true, studentOnIsland);
        islandModel.addStudent(ColorPawns.RED);
        islandModel.setTowerColor(ColorTower.GREY);

        testGame.getPlayersModel().get(0).addProf(ColorPawns.RED);

        testGame.getPlayersModel().get(1).addProf(ColorPawns.BLUE);
        testGame.getPlayersModel().get(1).addProf(ColorPawns.PINK);


        islandModel.getInfluence(testGame.getPlayersModel().get(0), null, true);
        assertEquals("davide", islandModel.getInfluence(testGame.getPlayersModel().get(0), null, true).getNickname());
        assertEquals("davide", islandModel.getInfluence(null, ColorPawns.BLUE, true).getNickname());

        islandModel.setTowerColor(ColorTower.BLACK);
        assertEquals("christian", islandModel.getInfluence(null, null, true).getNickname());

        assertTrue(islandModel.getMotherNature());
        islandModel.setMotherNature(false);
        assertFalse(islandModel.getMotherNature());


        assertFalse(islandModel.isJoined());
        islandModel.setJoined();
        assertTrue(islandModel.isJoined());

        assertTrue(islandModel.hasTower());
        assertFalse(islandModel.hasProhibition());
        islandModel.setHasProhibition(true);
        assertTrue(islandModel.hasProhibition());


        IslandModel island = new IslandModel(true, Arrays.asList(ColorPawns.BLUE, ColorPawns.RED ));

        /*testGame.getPlayersModel().get(1).removeProf(ColorPawns.PINK);
        assertNull(island.getInfluence(null, null, true).getNickname());*/


    }
}