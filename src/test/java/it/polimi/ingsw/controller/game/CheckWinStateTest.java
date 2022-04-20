package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckWinStateTest {

    @Test
    void playerWithMajorNumberOfTowerOnIslandIsGray() {
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 2);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.GREY);
        player3.setTowers(ColorTower.GREY, 0);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        player4.setTowers(ColorTower.BLACK, 0);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        CheckWinState tester = new CheckWinState(testGame);
        tester.setGameModel(testGame);
        assertEquals(ColorTower.GREY, tester.checkWin());
    }

    @Test
    void playerWithMajorNumberOfTowerOnIslandIsBlack() {
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 2);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.GREY);
        player3.setTowers(ColorTower.GREY, 0);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        player4.setTowers(ColorTower.BLACK, 0);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        CheckWinState tester = new CheckWinState(testGame);
        tester.setGameModel(testGame);
        assertEquals(ColorTower.BLACK, tester.checkWin());
    }

    @Test
    void playerWithMajorNumberOfProfsIsGrey() {
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        player1.addProf(ColorPawns.BLUE);
        player1.addProf(ColorPawns.PINK);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        player2.addProf(ColorPawns.GREEN);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.GREY);
        player3.setTowers(ColorTower.GREY, 0);
        player3.addProf(ColorPawns.RED);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        player4.setTowers(ColorTower.BLACK, 0);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        CheckWinState tester = new CheckWinState(testGame);
        tester.setGameModel(testGame);
        assertEquals(ColorTower.GREY, tester.checkWin());
    }

    @Test
    void playerWithMajorNumberOfProfsIsBlack() {
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<PlayerModel>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        player1.addProf(ColorPawns.BLUE);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        player2.addProf(ColorPawns.GREEN);
        player2.addProf(ColorPawns.PINK);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.GREY);
        player3.setTowers(ColorTower.GREY, 0);
        player3.addProf(ColorPawns.RED);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        player4.setTowers(ColorTower.BLACK, 0);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        CheckWinState tester = new CheckWinState(testGame);
        tester.setGameModel(testGame);
        assertEquals(ColorTower.BLACK, tester.checkWin());
    }
}