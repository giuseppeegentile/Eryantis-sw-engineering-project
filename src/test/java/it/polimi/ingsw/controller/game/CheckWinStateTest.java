package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckWinStateTest {
    @Test
    void playerWithMajorNumberOfTowerOnIsland(){
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = testGame.getPlayersModel();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 2);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player1.setTowers(ColorTower.BLACK, 5);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 8);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        player1.setTowers(ColorTower.BLACK, 3);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        CheckWinState tester = new CheckWinState();
        tester.setGameModel(testGame);
        assertEquals("GREY", "tester.checkWin()");
    }
}