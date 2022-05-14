package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CliTest {

    @Test
    void showOrderPhaseUpperRightCorner4(){
        Cli cli = new Cli();
        List<PlayerModel> playerOrder = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("Bin");
        PlayerModel player2 = new PlayerModel("Elfo");
        PlayerModel player3 = new PlayerModel("Luci");
        PlayerModel player4 = new PlayerModel("Stregobor");
        playerOrder.add(player1);
        playerOrder.add(player2);
        playerOrder.add(player3);
        playerOrder.add(player4);
        cli.showOrderPhase("Davide", playerOrder);
    }

    @Test
    void showOrderPhaseUpperRightCorner3(){
        Cli cli = new Cli();
        List<PlayerModel> playerOrder = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("Bin");
        PlayerModel player2 = new PlayerModel("Elfo");
        PlayerModel player3 = new PlayerModel("Luci");
        playerOrder.add(player1);
        playerOrder.add(player2);
        playerOrder.add(player3);
        cli.showOrderPhase("Davide", playerOrder);
    }

    @Test
    void showOrderPhaseUpperRightCorner2(){
        Cli cli = new Cli();
        List<PlayerModel> playerOrder = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("Bin");
        PlayerModel player2 = new PlayerModel("Elfo");
        playerOrder.add(player1);
        playerOrder.add(player2);
        cli.showOrderPhase("Davide", playerOrder);
    }

    @Test
    void showCards(){
        Cli cli = new Cli();
        PlayerModel player = new PlayerModel("John Snow");
        AssistantCardModel cardOne = new AssistantCardModel(5, (byte)4);
        AssistantCardModel cardTwo = new AssistantCardModel(3, (byte)2);
        AssistantCardModel cardThree = new AssistantCardModel(0, (byte)0);
        AssistantCardModel cardFour = new AssistantCardModel(6, (byte)1);
        List<AssistantCardModel> cards = new ArrayList<>();
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        player.setDeckAssistantCardModel(cards);
        cli.showCards(player);
    }
}