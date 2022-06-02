package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    static GameModel testGame = GameModel.getInstance();

    @Test
    @BeforeAll
    static void init(){
        testGame.endGame();

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
    void getPlayerByColorTowerTest() {
        String toCheck = testGame.getPlayerByColorTower(ColorTower.BLACK).getNickname();
        assertEquals("christian", toCheck);
    }

    @Test
    void playerWithMajorNumberOfTowerOnIslandIsGray() {
        GameModel.resetInstance();
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
        assertEquals(ColorTower.GREY, testGame.checkWin());
        assertTrue(testGame.isNicknameTaken("davide"));
        assertFalse(testGame.isNicknameTaken("aaaa"));
        assertEquals(player1, testGame.getPlayerByNickname("davide"));

        PlayerModel playerNull = testGame.getPlayerByColorTower(ColorTower.NULL);
        assertNull(playerNull.getNickname());

        AssistantCardModel cardToAdd = new AssistantCardModel(2,(byte)2);
        testGame.addToCemetery(cardToAdd);
        assertTrue(testGame.getCemetery().contains(cardToAdd));

        testGame.setGameMode(GameMode.PRINCIPIANTE);
        assertEquals(GameMode.PRINCIPIANTE, testGame.getGameMode());


        testGame.setBag(List.of(ColorPawns.BLUE, ColorPawns.RED));
        assertTrue(testGame.getBag().containsAll(List.of(ColorPawns.RED, ColorPawns.BLUE)));

        CloudModel cloud1 = new CloudModel(3);
        CloudModel cloud2 = new CloudModel(3);
        CloudModel cloud3 = new CloudModel(3);

        List<CloudModel> clouds = List.of(cloud1, cloud2, cloud3);

        testGame.setCloudsModel(clouds);
        assertEquals(clouds, testGame.getCloudsModel());

        testGame.setPlayerNumber(4);
        assertEquals(4, testGame.getPlayersNumber());


        List<AssistantCardModel> listDeck = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            AssistantCardModel c =new AssistantCardModel(1,(byte)1);
            listDeck.add(c);
            testGame.addCardToDeck(c);
        }

        assertEquals(listDeck, testGame.getDeck());

        testGame.setPhaseOrder(List.of(player2, player1, player3));
        assertEquals(List.of(player2, player1, player3), testGame.getPhaseOrder());
        testGame.clearPhaseOrder();
        assertEquals(List.of(), testGame.getPhaseOrder());

        testGame.removePlayerByNickname("quarto");
        assertFalse(testGame.getPlayersModel().contains(player4));
        testGame.addPlayer(player4);
        assertTrue(testGame.getPlayersModel().contains(player4));

        assertFalse(testGame.havePlayersFinishedCards());

        testGame.setTrueHavePlayerFinishedCards();
        assertTrue(testGame.havePlayersFinishedCards());
    }


    @Test
    void getIslandWithMotherNature(){
        IslandModel island = new IslandModel(true, ColorPawns.BLUE);
        List<IslandModel> islandModels = new ArrayList<>();
        islandModels.add(island);
        for (int i=1; i<12; i++){
            IslandModel island1 = new IslandModel(false, ColorPawns.PINK);
            islandModels.add(island1);
        }
        testGame.setIslands(islandModels);
        assertEquals(0, testGame.getMotherNatureIndex());
        assertEquals(island, testGame.getIslandWithMother());
        assertEquals(islandModels, testGame.getIslandsModel());


        testGame.getIslandsModel().get(4).setTowerColor(ColorTower.BLACK);
        testGame.getIslandsModel().get(5).setTowerColor(ColorTower.BLACK);

        testGame.getIslandsModel().get(7).setTowerColor(ColorTower.WHITE);

        testGame.getIslandsModel().get(9).setTowerColor(ColorTower.GREY);
        testGame.getIslandsModel().get(10).setTowerColor(ColorTower.GREY);
        testGame.getIslandsModel().get(11).setTowerColor(ColorTower.GREY);

        assertEquals(ColorDirectionAdjacentIsland.LEFT, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(5)));

        assertEquals(ColorDirectionAdjacentIsland.RIGHT, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(4)));

        assertEquals(ColorDirectionAdjacentIsland.BOTH, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(10)));


        assertEquals(ColorDirectionAdjacentIsland.NONE, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(1)));
        assertEquals(ColorDirectionAdjacentIsland.NONE, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(7)));

        testGame.getIslandsModel().get(11).setTowerColor(ColorTower.GREY);
        assertEquals(ColorDirectionAdjacentIsland.LEFT, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(11)));
        testGame.getIslandsModel().get(0).setTowerColor(ColorTower.GREY);
        assertEquals(ColorDirectionAdjacentIsland.LEFT, testGame.getAdjacentSameColor(testGame.getIslandsModel().get(0)));
    }


    @Test
    @AfterAll
    static void checkWinSameTower(){
        init();
        testGame.getPlayersModel().get(2).addProf(ColorPawns.PINK);
        testGame.checkWin();
        assertEquals(ColorTower.WHITE, testGame.checkWin());


        testGame.endGame();
    }
}