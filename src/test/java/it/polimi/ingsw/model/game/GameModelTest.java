package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void getPlayerByColorTower() {

        GameModel testGame = GameModel.getInstance();

        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.WHITE);
        player3.setTowers(ColorTower.GREY, 5);
        playersModels.add(player3);
        testGame.setPlayers(playersModels);
        String toCheck = testGame.getPlayerByColorTower(ColorTower.BLACK).getNickname();
        assertEquals("christian", toCheck);
    }
}