package it.polimi.ingsw.controller.game;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.PhaseGame;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GameControllerTest {
    GameController gameController = new GameController();
    List<String> players = new ArrayList<>();
    List<ColorTower> towers = new ArrayList<>();
    void init() {
        players.add("thirteen");
        players.add("house");
        players.add("wilson");
        players.add("foreman");
        towers.add(ColorTower.GREY);
        towers.add(ColorTower.BLACK);
        towers.add(ColorTower.GREY);
        towers.add(ColorTower.BLACK);
    }

    @Test
    void onMessageReceivedStart(){
/*        init();
        InitialConfigurationRequestMessage message = new InitialConfigurationRequestMessage(players, towers, GameMode.PRINCIPIANTE);
        gameController.setPhaseGame(PhaseGame.START);
        gameController.onMessageReceived(message);*/
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