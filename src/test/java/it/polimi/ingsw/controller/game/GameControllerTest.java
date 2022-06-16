package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Order(1)
    void testingGameTurn() {

        gameController.assignBag();
        gameController.generateDeck();
        PlayerNumberReply playerNumberReply = new PlayerNumberReply(player1, 3);
        gameInstance.setPlayers(asList(new PlayerModel(player1), new PlayerModel(player2), new PlayerModel(player3)));
        for (String s : asList(player1, player2, player3)){
            gameController.setOwnerDeck(s);
            gameController.assignCardsToPlayer(s);
        }

        GameModeRes gameModeRes = new GameModeRes(player1, GameMode.BEGINNER);
        gameController.onMessageReceived(gameModeRes);

        ChosenTowerMessage chosenTowerMessage = new ChosenTowerMessage(player1, tower1);
        gameController.onMessageReceived(chosenTowerMessage);
        chosenTowerMessage = new ChosenTowerMessage(player2, tower2);
        gameController.onMessageReceived(chosenTowerMessage);
        chosenTowerMessage = new ChosenTowerMessage(player2, tower3);
        gameController.onMessageReceived(chosenTowerMessage);

        assertEquals(gameInstance.getPlayersModel().get(0).getNickname(), player1);
        assertEquals(gameInstance.getPlayersModel().get(0).getColorTower(), ColorTower.BLACK);

        PlayerNicknameMessage message2 = new PlayerNicknameMessage(this.player2, 3, tower2, GameMode.BEGINNER);
        gameController.onMessageReceived(message2);
        assertEquals(gameInstance.getPlayersModel().get(1).getNickname(), player2);
        assertEquals(gameInstance.getPlayersModel().get(1).getColorTower(), ColorTower.WHITE);

        PlayerNicknameMessage message3 = new PlayerNicknameMessage(this.player3, 3, tower3, GameMode.BEGINNER);
        gameController.onMessageReceived(message3);
        assertEquals(gameInstance.getPlayersModel().get(2).getNickname(), player3);
        assertEquals(gameInstance.getPlayersModel().get(2).getColorTower(), ColorTower.GREY);

        //DA TESTARE CON 4 giocatori per vedere se assegnamento torri è giusto

        assertEquals(0, gameInstance.getPlayersModel().get(0).getStudentInHall().get(ColorPawns.RED));
        assertEquals(0, gameInstance.getPlayersModel().get(2).getStudentInHall().get(ColorPawns.BLUE));
        assertEquals(4, gameInstance.getCloudsModel().get(0).getStudents().size());
        assertEquals(gameInstance.getPlayersModel().get(0), gameController.getPlayerActive());

        assertEquals(3, gameInstance.getCloudsModel().size());

        gameInstance.getPlayersModel().forEach(p -> {
            assertEquals(6, p.getTowerNumber());
            assertEquals(9, p.getStudentInEntrance().size());
        });
        assertEquals(gameInstance.getPlayersModel(), gameInstance.getPhaseOrder());
        assertEquals(10, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().size());


        //giocano carte
        int priority1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getPriority();
        int movement1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getMotherNatureMovement();
        PlayAssistantCardMessage msgCardPl1 = new PlayAssistantCardMessage(player1, 5);
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getMotherNatureMovement());
        assertEquals(1, gameInstance.getCemetery().size());

        assertEquals(priority1, gameInstance.getCemetery().get(0).getPriority());
        assertEquals(movement1, gameInstance.getCemetery().get(0).getMotherNatureMovement());

        int priority2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getPriority();
        int movement2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getMotherNatureMovement();
        PlayAssistantCardMessage msgCardPl2 = new PlayAssistantCardMessage(player2, 5);
        gameController.onMessageReceived(msgCardPl2);

        assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getMotherNatureMovement());
        assertEquals(2, gameInstance.getCemetery().size());

        assertEquals(priority2, gameInstance.getCemetery().get(1).getPriority());
        assertEquals(movement2, gameInstance.getCemetery().get(1).getMotherNatureMovement());


        int priority3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getPriority();
        int movement3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getMotherNatureMovement();
        PlayAssistantCardMessage msgCardPl3 = new PlayAssistantCardMessage(player3, 3);
        gameController.onMessageReceived(msgCardPl3);

        assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getMotherNatureMovement());
        assertEquals(3, gameInstance.getCemetery().size());
        System.out.println("carta " + player1 + " " + gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getMotherNatureMovement());
        System.out.println("carta " + player2 + " " + gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getMotherNatureMovement());
        System.out.println("carta " + player3 + " " + gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getMotherNatureMovement() );
        System.out.println("cimitero:");
        System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );


        assertEquals(priority3, gameInstance.getCemetery().get(2).getPriority());
        assertEquals(movement3, gameInstance.getCemetery().get(2).getMotherNatureMovement());

        int i = 0;
        for (AssistantCardModel cardPlayed : gameInstance.getCemetery()) {
            assertEquals(gameInstance.getPlayersModel().get(i).getNickname(), cardPlayed.getOwner().getNickname());
            i++;
        }

        for(PlayerModel p: gameInstance.getPhaseOrder()){
            System.out.println(p.getNickname());
        }

        //turno primo giocatore

        PlayerModel firstPlayer = gameInstance.getPhaseOrder().get(0);
        System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));
        ColorPawns student1ToMove = firstPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns student2ToMove = firstPlayer.getStudentInEntrance().get(1);
        List<ColorPawns> playersToMove = new ArrayList<>();
        playersToMove.add(student1ToMove);
        playersToMove.add(student2ToMove);
        //StudentToIslandMessage msgPlayer1 = new StudentToIslandMessage(firstPlayer.getNickname(), playersToMove, 1);
        //gameController.onMessageReceived(msgPlayer1);
        assertEquals(gameController.getPlayerActive().getNickname(), firstPlayer.getNickname());

        //gameInstance.getIslandsModel().get(1).getStudents().forEach(System.out::println);
        assertTrue(gameInstance.getIslandsModel().get(1).getStudents().containsAll(playersToMove));

        ColorPawns color = firstPlayer.getStudentInEntrance().get(0);
        ColorPawns color2 = firstPlayer.getStudentInEntrance().get(1);
        assertEquals(firstPlayer, gameController.getPlayerActive());
        MovedStudentToHallMessage toHallMessage = new MovedStudentToHallMessage(gameController.getPlayerActive().getNickname(), List.of(color, color2));
        gameController.onMessageReceived(toHallMessage);
        if(color != color2) {
            assertEquals(1, firstPlayer.getStudentInHall().get(color));
            assertEquals(1, firstPlayer.getStudentInHall().get(color2));
        }
        else
            assertEquals(2, firstPlayer.getStudentInHall().get(color));



        int indexOldMother = gameInstance.getMotherNatureIndex();

        MovedMotherNatureMessage motherNatureMessage = new MovedMotherNatureMessage(firstPlayer.getNickname(), (byte) 1);
        gameController.onMessageReceived(motherNatureMessage);

        assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother + 1) % 11);


        AddStudentFromCloudToEntranceMessage msgCloudToWaiting = new AddStudentFromCloudToEntranceMessage(firstPlayer.getNickname(), 1);
        gameController.onMessageReceived(msgCloudToWaiting);
        //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
        assertEquals(0, gameInstance.getCloudsModel().get(1).getStudents().size());

        //turno secondo giocatore
        PlayerModel secondPlayer = gameInstance.getPhaseOrder().get(1);
        System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));
        ColorPawns secondStudent1ToMove = secondPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns secondStudent2ToMove = secondPlayer.getStudentInEntrance().get(1);
        ColorPawns secondStudent3ToMove = secondPlayer.getStudentInEntrance().get(2);
        List<ColorPawns> secondStudentsToMove = new ArrayList<>();
        secondStudentsToMove.add(secondStudent1ToMove);
        secondStudentsToMove.add(secondStudent2ToMove);
        secondStudentsToMove.add(secondStudent3ToMove);
        // StudentToIslandMessage msgPlayer2 = new StudentToIslandMessage(secondPlayer.getNickname(), secondStudentsToMove, 5);
        //gameController.onMessageReceived(msgPlayer2);
        assertEquals(gameController.getPlayerActive().getNickname(), secondPlayer.getNickname());

        //gameInstance.getIslandsModel().get(1).getStudents().forEach(System.out::println);
        assertTrue(gameInstance.getIslandsModel().get(5).getStudents().containsAll(secondStudentsToMove));

        color2 = secondPlayer.getStudentInEntrance().get(0);

        assertEquals(secondPlayer, gameController.getPlayerActive());
        MovedStudentToHallMessage toHallMessage2 = new MovedStudentToHallMessage(gameController.getPlayerActive().getNickname(), List.of(color2));
        gameController.onMessageReceived(toHallMessage2);
        assertEquals(1, secondPlayer.getStudentInHall().get(color2));


        indexOldMother = gameInstance.getMotherNatureIndex();
        MovedMotherNatureMessage motherNatureMessage2 = new MovedMotherNatureMessage(secondPlayer.getNickname(), (byte) 1);
        gameController.onMessageReceived(motherNatureMessage2);

        assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother+1)%11);

        AddStudentFromCloudToEntranceMessage msgCloudToWaiting2 = new AddStudentFromCloudToEntranceMessage(secondPlayer.getNickname(), 0);
        gameController.onMessageReceived(msgCloudToWaiting2);
        assertEquals(0, gameInstance.getCloudsModel().get(0).getStudents().size());

        //turno terzo giocatore
        PlayerModel thirdPlayer = gameInstance.getPhaseOrder().get(2);
        ColorPawns thirdStudent1ToMove = thirdPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns thirdStudent2ToMove = thirdPlayer.getStudentInEntrance().get(1);
        List<ColorPawns> thirdStudentsToMove = new ArrayList<>();
        thirdStudentsToMove.add(thirdStudent1ToMove);
        thirdStudentsToMove.add(thirdStudent2ToMove);




        //StudentToIslandMessage msgPlayer3 = new StudentToIslandMessage(thirdPlayer.getNickname(), thirdStudentsToMove, 8);
        //gameController.onMessageReceived(msgPlayer3);
        assertEquals(gameController.getPlayerActive().getNickname(), thirdPlayer.getNickname());

        //gameInstance.getIslandsModel().get(1).getStudents().forEach(System.out::println);
        assertTrue(gameInstance.getIslandsModel().get(8).getStudents().containsAll(thirdStudentsToMove));

        ColorPawns color3 = thirdPlayer.getStudentInEntrance().get(0);
        ColorPawns color4 = thirdPlayer.getStudentInEntrance().get(1);
        assertEquals(thirdPlayer, gameController.getPlayerActive());
        MovedStudentToHallMessage toHallMessage3 = new MovedStudentToHallMessage(gameController.getPlayerActive().getNickname(), List.of(color3,color4));
        gameController.onMessageReceived(toHallMessage3);
        if(color3 != color4) {
            assertEquals(1, thirdPlayer.getStudentInHall().get(color3));
            assertEquals(1, thirdPlayer.getStudentInHall().get(color4));
        }
        else
            assertEquals(2, thirdPlayer.getStudentInHall().get(color3));

        indexOldMother = gameInstance.getMotherNatureIndex();
        MovedMotherNatureMessage motherNatureMessage3 = new MovedMotherNatureMessage(thirdPlayer.getNickname(), (byte) 1);
        gameController.onMessageReceived(motherNatureMessage3);
        assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother+1)%11);

        AddStudentFromCloudToEntranceMessage msgCloudToWaiting3 = new AddStudentFromCloudToEntranceMessage(thirdPlayer.getNickname(), 2);
        gameController.onMessageReceived(msgCloudToWaiting3);
        //System.out.println(gameInstance.getCloudsModel().get(0).getStudents());
        //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
        //System.out.println(gameInstance.getCloudsModel().get(2).getStudents());
        assertEquals(4, gameInstance.getCloudsModel().get(2).getStudents().size());


        assertEquals(10, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().size());

        priority1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getPriority();
        movement1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getMotherNatureMovement();
        msgCardPl1 = new PlayAssistantCardMessage(player1, 2);
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getMotherNatureMovement());
        assertEquals(1, gameInstance.getCemetery().size());

        assertEquals(priority1, gameInstance.getCemetery().get(0).getPriority());
        assertEquals(movement1, gameInstance.getCemetery().get(0).getMotherNatureMovement());

        priority2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getPriority();
        movement2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getMotherNatureMovement();
        msgCardPl2 = new PlayAssistantCardMessage(player2, 7);
        gameController.onMessageReceived(msgCardPl2);


        assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getMotherNatureMovement());
        assertEquals(2, gameInstance.getCemetery().size());

        assertEquals(priority2, gameInstance.getCemetery().get(1).getPriority());
        assertEquals(movement2, gameInstance.getCemetery().get(1).getMotherNatureMovement());


        priority3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getPriority();
        movement3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getMotherNatureMovement();
        msgCardPl3 = new PlayAssistantCardMessage(player3, 8);

        gameController.onMessageReceived(msgCardPl3);

        assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getMotherNatureMovement());
        assertEquals(3, gameInstance.getCemetery().size());

        System.out.println("carta " + player1 + " " + gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getMotherNatureMovement() );
        System.out.println("carta " + player2 + " " + gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getMotherNatureMovement() );
        System.out.println("carta " + player3 + " " + gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getMotherNatureMovement() );
        System.out.println("cimitero:");
        System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );


        assertEquals(priority3, gameInstance.getCemetery().get(2).getPriority());
        assertEquals(movement3, gameInstance.getCemetery().get(2).getMotherNatureMovement());



        for(PlayerModel p: gameInstance.getPhaseOrder()){
            System.out.println(p.getNickname());
        }

    }


    @Test
    void computeIslandChangesWithoutMovingMother(){
        PlayerModel player1 = new PlayerModel("Tony Stark", ColorTower.GREY);
        PlayerModel player2 = new PlayerModel("Pepper Pots", ColorTower.BLACK);
        player1.addProf(ColorPawns.BLUE);
        player2.addProf(ColorPawns.RED);
        List<PlayerModel> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        List<ColorPawns> studentOnIsland = new ArrayList<>(asList(ColorPawns.BLUE,ColorPawns.RED,ColorPawns.RED,ColorPawns.RED));
        IslandModel islandModel = new IslandModel(true, studentOnIsland);
        List<IslandModel> islands = new ArrayList<>();
        islandModel.setTowerColor(ColorTower.GREY);
        islands.add(islandModel);
        for(int i=0; i<11; i++){
            IslandModel islandModel1 = new IslandModel(false, ColorPawns.BLUE);
            islands.add(islandModel1);
        }
        GameController gameController = new GameController();
        gameInstance.setIslands(islands);
        gameInstance.setPlayers(players);
        gameController.setIgnoreColorEffect(null);
        gameController.setConsiderTower(true);
        gameController.setPlayerWithEffectAdditionalInfluence(null);
        gameController.computeIslandsChanges(player2, islandModel);
        assertEquals(ColorTower.BLACK, islandModel.getTowerColor());
    }

    @Test
    void getAvailableClouds(){
        CloudModel cloud1 = new CloudModel(3);
        CloudModel cloud2 = new CloudModel(3);
        List<ColorPawns> studentOnIsland = new ArrayList<>(asList(ColorPawns.BLUE,ColorPawns.RED,ColorPawns.RED,ColorPawns.RED));
        List<ColorPawns> studentOnIsland1 = new ArrayList<>();
        cloud1.setStudents(studentOnIsland);
        cloud2.setStudents(studentOnIsland1);
        List<CloudModel> clouds = new ArrayList<>();
        clouds.add(cloud1);
        clouds.add(cloud2);
        gameInstance.setCloudsModel(clouds);
        assertEquals(cloud1, gameController.getAvailableClouds().get(0));
    }
}