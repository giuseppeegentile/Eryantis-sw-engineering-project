package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.controller.game.DecideFirstPlayerState;
import it.polimi.ingsw.controller.game.StartGameState;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayCardAssistantStateTest {
    GameModel testGame = GameModel.getInstance();
    StartGameState ssg = new StartGameState(testGame);
    PlayerModel player1 = new PlayerModel("davide");
    PlayerModel player2 = new PlayerModel("christian");
    PlayerModel player3 = new PlayerModel("giuseppe");
    PlayerModel player4 = new PlayerModel("quarto");
    List<PlayCardAssistantState> plays = new ArrayList<>(4);

    AssistantCardModel cardOne;
    AssistantCardModel cardTwo;

    AssistantCardModel cardThree;
    AssistantCardModel cardFour;


    void init(){

        List<PlayerModel> playersModels = new ArrayList<>();
        playersModels.add(player1);
        playersModels.add(player2);
        playersModels.add(player3);
        playersModels.add(player4);
        List<ColorTower> colorTowers = new ArrayList<>(Arrays.asList(ColorTower.BLACK, ColorTower.WHITE, ColorTower.BLACK, ColorTower.WHITE));
        GameMode princ = GameMode.PRINCIPIANTE;

        ssg.setInitialGameConfiguration(playersModels, colorTowers, princ);
    }

    void testPlayCard(){
        init();

        PlayCardAssistantState playCardPlayerOne = new PlayCardAssistantState(player1, testGame);
        PlayCardAssistantState playCardPlayerTwo = new PlayCardAssistantState(player2, testGame);
        PlayCardAssistantState playCardPlayerThree = new PlayCardAssistantState(player3, testGame);
        PlayCardAssistantState playCardPlayerFour = new PlayCardAssistantState(player4, testGame);
        plays.add(playCardPlayerOne);
        plays.add(playCardPlayerTwo);
        plays.add(playCardPlayerThree);
        plays.add(playCardPlayerFour);
        int i = 0;

        while(
                !playCardPlayerOne.canPlayCard(player1.getDeckAssistantCardModel().get(i)) &&
                !playCardPlayerTwo.canPlayCard(player2.getDeckAssistantCardModel().get(i)) &&
                !playCardPlayerThree.canPlayCard(player3.getDeckAssistantCardModel().get(i))&&
                !playCardPlayerFour.canPlayCard(player4.getDeckAssistantCardModel().get(i))) {
            i++;
            if(i == 10) return;
        }
        plays.forEach(p->{
            p.getPlayerModel().getDeckAssistantCardModel().forEach(c->{
                assertTrue(p.canPlayCard(c));
            });
        });


        cardOne = player1.getDeckAssistantCardModel().get(i);
        cardTwo = player2.getDeckAssistantCardModel().get(i);

        cardThree = player3.getDeckAssistantCardModel().get(i);
        cardFour = player4.getDeckAssistantCardModel().get(i);

        System.out.println(cardOne.getPriority());
        System.out.println(cardTwo.getPriority());
        System.out.println(cardThree.getPriority());
        System.out.println(cardFour.getPriority());

        playCardPlayerOne.playCard(cardOne);

        playCardPlayerTwo.playCard(cardTwo);
        playCardPlayerThree.playCard(cardThree);
        playCardPlayerFour.playCard(cardFour);


        assertTrue(this.testGame.getCemetery().contains(cardOne));
        assertTrue(this.testGame.getCemetery().contains(cardTwo));
        assertTrue(this.testGame.getCemetery().contains(cardThree));
        assertTrue(this.testGame.getCemetery().contains(cardFour));

        assertFalse(playCardPlayerOne.canPlayCard(player2.getDeckAssistantCardModel().get(4)));
        assertTrue(playCardPlayerOne.canPlayCard(player1.getDeckAssistantCardModel().get(4)));

        assertFalse(playCardPlayerOne.canPlayCard(player1.getDeckAssistantCardModel().get(0)));
        assertFalse(playCardPlayerOne.canPlayCard(player2.getDeckAssistantCardModel().get(0)));
        assertFalse(playCardPlayerOne.canPlayCard(player3.getDeckAssistantCardModel().get(0)));
        assertFalse(playCardPlayerOne.canPlayCard(player4.getDeckAssistantCardModel().get(0)));
    }

    @Test
    @Order(2)
    void setPlayersOrderForActionPhaseTest(){
        testPlayCard();
        DecideFirstPlayerState playCardAssistantState = new DecideFirstPlayerState(testGame);

        playCardAssistantState.setPlayersOrderForActionPhase(this.testGame.getCemetery());

        if(Collections.min(this.testGame.getCemetery(), Comparator.comparing(AssistantCardModel::getPriority)) == cardOne){
            assertEquals(player1, this.testGame.getPhaseOrder().get(0));
        }
        if(Collections.min(this.testGame.getCemetery(), Comparator.comparing(AssistantCardModel::getPriority)) == cardTwo){
            assertEquals(player2, this.testGame.getPhaseOrder().get(0));
        }
        if(Collections.min(this.testGame.getCemetery(), Comparator.comparing(AssistantCardModel::getPriority)) == cardThree){
            assertEquals(player3, this.testGame.getPhaseOrder().get(0));
        }
        if(Collections.min(this.testGame.getCemetery(), Comparator.comparing(AssistantCardModel::getPriority)) == cardFour){
            assertEquals(player4, this.testGame.getPhaseOrder().get(0));
        }
        System.out.println("Giocatori in ordine di gioco: ");

        this.testGame.getPhaseOrder().forEach(p->{
            System.out.println(p.getNickname());
        });

        assertEquals(player1.getMovementMotherNatureCurrentActionPhase(), cardOne.getMotherNatureMovement());
        assertEquals(player2.getMovementMotherNatureCurrentActionPhase(), cardTwo.getMotherNatureMovement());
        assertEquals(player3.getMovementMotherNatureCurrentActionPhase(), cardThree.getMotherNatureMovement());
        assertEquals(player4.getMovementMotherNatureCurrentActionPhase(), cardFour.getMotherNatureMovement());



    }
}