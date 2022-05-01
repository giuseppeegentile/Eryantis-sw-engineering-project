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
    void firstOfFourPlayer(){
        GameModel testGame = GameModel.getInstance();
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("jack", ColorTower.GREY);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("john", ColorTower.BLACK);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("al", ColorTower.GREY);
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto", ColorTower.BLACK);
        playersModels.add(player4);
        testGame.setPlayers(playersModels);
        List<AssistantCardModel> cemetery = new ArrayList<AssistantCardModel>();
        AssistantCardModel card1 = new AssistantCardModel(4, (byte) 5);
        card1.setOwner(player1);
        AssistantCardModel card2 = new AssistantCardModel(2, (byte) 2);
        card2.setOwner(player2);
        AssistantCardModel card3 = new AssistantCardModel(6, (byte) 5);
        card3.setOwner(player3);
        AssistantCardModel card4 = new AssistantCardModel(1, (byte) 3);
        card4.setOwner(player4);
        DecideOrderPlayerState decide = new DecideOrderPlayerState(testGame);
        decide.setPlayersOrderForActionPhase(cemetery);
        assertEquals("quarto", testGame.getPhaseOrder().get(0).getNickname());
    }
}