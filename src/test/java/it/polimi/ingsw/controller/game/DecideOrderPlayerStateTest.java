package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DecideOrderPlayerStateTest {

    @Test
    void orderWithFourPlayer(){
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        List<AssistantCardModel> cemetery = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("jack", ColorTower.GREY);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("john", ColorTower.BLACK);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("al", ColorTower.GREY);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        AssistantCardModel card1 = new AssistantCardModel(4, (byte) 5);
        card1.setOwner(player1);
        cemetery.add(card1);
        AssistantCardModel card2 = new AssistantCardModel(2, (byte) 2);
        card2.setOwner(player2);
        cemetery.add(card2);
        AssistantCardModel card3 = new AssistantCardModel(6, (byte) 5);
        card3.setOwner(player3);
        cemetery.add(card3);
        AssistantCardModel card4 = new AssistantCardModel(1, (byte) 3);
        card4.setOwner(player4);
        cemetery.add(card4);
        DecideOrderPlayerState decide = new DecideOrderPlayerState(testGame);
        decide.setPlayersOrderForActionPhase(cemetery);
        assertEquals("quarto", testGame.getPhaseOrder().get(0).getNickname());
        assertEquals("john", testGame.getPhaseOrder().get(1).getNickname());
        assertEquals("jack", testGame.getPhaseOrder().get(2).getNickname());
        assertEquals("al", testGame.getPhaseOrder().get(3).getNickname());
    }
}