package it.polimi.ingsw.controller.game;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.network.message.PlayerNicknameMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameControllerTest {
    private final GameController gameController = new GameController();
    private final String player1 = "pl1";
    private final ColorTower tower1 = ColorTower.BLACK;
    private final GameModel gameInstance = GameModel.getInstance();
    private final String player2 = "pl2";
    private final ColorTower tower2 = ColorTower.WHITE;
    private final String player3 = "pl3";
    private final ColorTower tower3 = ColorTower.GREY;

    @Test
    void onMessageReceivedStart(){
        gameController.setPhaseGame(PhaseGame.START);
        PlayerNicknameMessage message = new PlayerNicknameMessage(this.player1, 3, tower1, GameMode.PRINCIPIANTE);

        gameController.onMessageReceived(message);
        assertEquals(gameInstance.getPlayersModel().get(0).getNickname(), player1);
        assertEquals(gameInstance.getPlayersModel().get(0).getColorTower(), ColorTower.BLACK);

        PlayerNicknameMessage message2 = new PlayerNicknameMessage(this.player2, 3, tower2, GameMode.PRINCIPIANTE);
        gameController.onMessageReceived(message2);
        assertEquals(gameInstance.getPlayersModel().get(1).getNickname(), player2);
        assertEquals(gameInstance.getPlayersModel().get(1).getColorTower(), ColorTower.WHITE);
        assertEquals(PhaseGame.START, gameController.getPhaseGame());

        PlayerNicknameMessage message3 = new PlayerNicknameMessage(this.player3, 3, tower3, GameMode.PRINCIPIANTE);
        gameController.onMessageReceived(message3);
        assertEquals(gameInstance.getPlayersModel().get(2).getNickname(), player3);
        assertEquals(gameInstance.getPlayersModel().get(2).getColorTower(), ColorTower.GREY);
        assertEquals(PhaseGame.PLAY_CARDS_ASSISTANT, gameController.getPhaseGame());

        //DA TESTARE CON 4 per il discorso delle torri

        assertEquals( 0,gameInstance.getPlayersModel().get(0).getStudentInHall().get(ColorPawns.RED));
        assertEquals(0,gameInstance.getPlayersModel().get(2).getStudentInHall().get(ColorPawns.BLUE));
        assertEquals(4, gameInstance.getCloudsModel().get(0).getStudents().size());
        assertEquals(gameInstance.getPlayersModel().get(0), gameController.getPlayerActive());
    }

    @Test
    void onMessageReceivedAddStudentToIsland(){
/*        IslandModel island = new IslandModel(true);
        String player = "";
        StudentToIslandMessage message = new StudentToIslandMessage(player, ColorPawns.BLUE, island);
        gameController.setPhaseGame(PhaseGame.ADD_STUDENT_TO_ISLAND);
        gameController.onMessageReceived(message);*/
    }

    @Test
    void onMessageReceivedAddStudentToHall(){
        /*String player = "";
        MoveMessage message = new MoveMessage(player, ColorPawns.BLUE);
        gameController.setPhaseGame(PhaseGame.ADD_STUDENT_TO_HALL);
        gameController.onMessageReceived(message);*/
    }

    @Test
    void onMessageReceivedMoveMother(){
        gameController.setPhaseGame(PhaseGame.MOVE_MOTHER);
        //gameController.onMessageReceived();
    }

    @Test
    void onMessageReceivedPlayerMoveFromCloudToEntrance(){
        gameController.setPhaseGame(PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE);
        //gameController.onMessageReceived();
    }

    @Test
    void onMessageReceivedPlayCardsAssistant(){
        gameController.setPhaseGame(PhaseGame.PLAY_CARDS_ASSISTANT);
        //gameController.onMessageReceived();
    }
}