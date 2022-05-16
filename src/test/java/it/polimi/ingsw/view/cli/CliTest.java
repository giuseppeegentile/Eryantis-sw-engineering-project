package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
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
        AssistantCardModel cardOne = new AssistantCardModel(5, (byte)4);
        AssistantCardModel cardTwo = new AssistantCardModel(3, (byte)2);
        AssistantCardModel cardThree = new AssistantCardModel(0, (byte)0);
        AssistantCardModel cardFour = new AssistantCardModel(6, (byte)1);
        List<AssistantCardModel> cards = new ArrayList<>();
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        cli.showDeckMessage("John Snow", cards);
    }

    @Test
    void printEriantys(){
        Cli cli = new Cli();
        cli.init();
    }

    @Test
    void showEndOfTurn(){
        Cli cli = new Cli();
        cli.showEndTurn("Harry Potter");
    }

    @Test
    void showInvalidNickname(){
        Cli cli = new Cli();
        cli.showInvalidNickname("Percy Jackson");
    }

    @Test
    void showAssistantCardPlayed(){
        Cli cli = new Cli();
        AssistantCardModel card = new AssistantCardModel(5, (byte)2);
        cli.showPlayAssistantCardMessage("Jovanotti", card);
    }

    @Test
    void showStudentsOnClouds(){
        Cli cli = new Cli();
        List<CloudModel> clouds = new ArrayList<>();
        CloudModel cloud1 = new CloudModel(4);
        CloudModel cloud2 = new CloudModel(4);
        List<ColorPawns> lista = new ArrayList<>();
        lista.add(ColorPawns.GREEN);
        lista.add(ColorPawns.RED);
        lista.add(ColorPawns.BLUE);
        lista.add(ColorPawns.GREEN);
        cloud1.setStudents(lista);
        lista.removeAll(lista);
        lista.add(ColorPawns.GREEN);
        lista.add(ColorPawns.RED);
        lista.add(ColorPawns.BLUE);
        lista.add(ColorPawns.GREEN);
        cloud2.setStudents(lista);
        clouds.add(cloud1);
        clouds.add(cloud2);
        cli.showCloudsMessage("Strange", clouds);
    }

    @Test
    void showUpdatedIslandWithoutMother(){
        List<ColorPawns> students = new ArrayList<>();
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.YELLOW);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        IslandModel islandModel = new IslandModel(Boolean.FALSE, students);
        islandModel.setTowerColor(ColorTower.BLACK);
        Cli cli = new Cli();
        cli.showIslandMessage("Geralt di Rivia",islandModel, 5);
    }

    @Test
    void showUpdatedIslandWithMother(){
        List<ColorPawns> students = new ArrayList<>();
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.YELLOW);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        IslandModel islandModel = new IslandModel(Boolean.TRUE, students);
        islandModel.setTowerColor(ColorTower.BLACK);
        Cli cli = new Cli();
        cli.showIslandMessage("Geralt di Rivia",islandModel, 5);
    }

    @Test
    void showIslands(){
        List<ColorPawns> students = new ArrayList<>();
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.YELLOW);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.RED);
        students.add(ColorPawns.GREEN);
        students.add(ColorPawns.BLUE);
        IslandModel islandModel = new IslandModel(Boolean.FALSE, students);
        islandModel.setTowerColor(ColorTower.BLACK);
        IslandModel islandModel2 = new IslandModel(Boolean.FALSE, students);
        islandModel2.setTowerColor(ColorTower.BLACK);
        IslandModel islandModel3 = new IslandModel(Boolean.FALSE, students);
        islandModel3.setTowerColor(ColorTower.GREY);
        IslandModel islandModel4 = new IslandModel(Boolean.FALSE, students);
        islandModel4.setTowerColor(ColorTower.WHITE);
        IslandModel islandModel5 = new IslandModel(Boolean.FALSE, students);
        islandModel5.setTowerColor(ColorTower.WHITE);
        IslandModel islandModel6 = new IslandModel(Boolean.FALSE, students);
        islandModel6.setTowerColor(ColorTower.GREY);
        IslandModel islandModel7 = new IslandModel(Boolean.TRUE, students);
        islandModel7.setTowerColor(ColorTower.BLACK);
        IslandModel islandModel8 = new IslandModel(Boolean.FALSE, students);
        islandModel8.setTowerColor(ColorTower.BLACK);
        IslandModel islandModel9 = new IslandModel(Boolean.FALSE, students);
        islandModel9.setTowerColor(ColorTower.GREY);
        IslandModel islandModel10 = new IslandModel(Boolean.FALSE, students);
        islandModel10.setTowerColor(ColorTower.WHITE);
        IslandModel islandModel11 = new IslandModel(Boolean.FALSE, students);
        islandModel11.setTowerColor(ColorTower.WHITE);
        IslandModel islandModel12 = new IslandModel(Boolean.FALSE, students);
        islandModel12.setTowerColor(ColorTower.GREY);
        List<IslandModel> islands = new ArrayList<>();
        islands.add(islandModel);
        islands.add(islandModel2);
        islands.add(islandModel3);
        islands.add(islandModel4);
        islands.add(islandModel5);
        islands.add(islandModel6);
        islands.add(islandModel7);
        islands.add(islandModel8);
        islands.add(islandModel9);
        islands.add(islandModel10);
        islands.add(islandModel11);
        islands.add(islandModel12);
        Cli cli = new Cli();
        cli.showIslands("Batman", islands);
    }
}