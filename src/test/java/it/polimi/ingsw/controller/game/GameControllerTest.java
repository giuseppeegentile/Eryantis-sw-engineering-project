package it.polimi.ingsw.controller.game;
import com.sun.jdi.Value;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.InitialConfigurationRequestMessage;
import it.polimi.ingsw.network.message.MoveMessage;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        init();
        InitialConfigurationRequestMessage message = new InitialConfigurationRequestMessage(players, towers, GameMode.PRINCIPIANTE);
        gameController.setPhaseGame(PhaseGame.START);
        gameController.onMessageReceived(message);
    }

    @Test
    void onMessageReceivedAddStudentToIsland(){
        IslandModel island = new IslandModel(true);
        PlayerModel player = new PlayerModel();
        MoveMessage message = new MoveMessage(player, ColorPawns.BLUE, island);
        gameController.setPhaseGame(PhaseGame.ADD_STUDENT_TO_ISLAND);
        gameController.onMessageReceived(message);
    }

    @Test
    void onMessageReceivedAddStudentToHall(){
        PlayerModel player = new PlayerModel();
        MoveMessage message = new MoveMessage(player, ColorPawns.BLUE);
        gameController.setPhaseGame(PhaseGame.ADD_STUDENT_TO_HALL);
        gameController.onMessageReceived(message);
    }

    @Test
    void onMessageReceivedMoveMother(){
        gameController.setPhaseGame(PhaseGame.MOVE_MOTHER);
        gameController.onMessageReceived();
    }

    @Test
    void onMessageReceivedPlayerMoveFromCloudToEntrance(){
        gameController.setPhaseGame(PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE);
        gameController.onMessageReceived();
    }

    @Test
    void onMessageReceivedPlayCardsAssistant(){
        gameController.setPhaseGame(PhaseGame.PLAY_CARDS_ASSISTANT);
        gameController.onMessageReceived();
    }
}