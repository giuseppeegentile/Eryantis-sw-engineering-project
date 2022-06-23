package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerAdvancedTest2 {

    private static GameModel gameInstance;
    private static GameController gameController;
    private static ClientHandler clientHandler;

    static String player1 = "Milan";
    static String player2 = "Juventus";
    static String player3 = "Inter";
    ColorTower tower1 = ColorTower.BLACK;
    ColorTower tower2 = ColorTower.WHITE;
    ColorTower tower3 = ColorTower.GREY;
    AssistantCardModel card1 = new AssistantCardModel(1, (byte)1);
    AssistantCardModel card2 = new AssistantCardModel(2, (byte)1);
    AssistantCardModel card3 = new AssistantCardModel(3, (byte)1);
    List<ColorPawns> entrance = new ArrayList<>(List.of(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED));

    @BeforeAll
    public static void setUp() {
        gameController = new GameController();
        gameController.setShuffleFalse();
        gameController.incrementTest(3);
        gameInstance = GameModel.getInstance();

        clientHandler = new ClientHandler() {
            @Override
            public boolean isConnected() {
                return true;
            }

            @Override
            public void disconnect() {

            }

            @Override
            public void sendMessage(Message message) {

            }
        };

        connectAndSetup(player1, player2, player3);
    }

    @AfterAll
    public static void tearDown() {

        GameModel.resetInstance();

        gameController = null;
        clientHandler = null;
    }

    private static void connectAndSetup(String p1, String p2, String p3) {

        Server server = new Server(gameController);
        server.addClient(p1, clientHandler);
        PlayerNumberReply playerNumberReply = new PlayerNumberReply(p1, 3);
        gameController.onMessageReceived(playerNumberReply);
        server.addClient(p2, clientHandler);
        server.addClient(p3, clientHandler);
        /*LoginRequest loginRequest = new LoginRequest(p1);
        gameController.onMessageReceived(playerNumberReply);
        LoginRequest loginRequest2 = new LoginRequest(p2);
        gameController.onMessageReceived(loginRequest2);
        LoginRequest loginRequest3 = new LoginRequest(p3);
        gameController.onMessageReceived(loginRequest3);*/

    }

    @Test
    void testingGameTurnAdvanced(){
        //System.out.println(gameInstance.getPlayersModel().get(0) + " " + gameInstance.getPlayersModel().get(1) + " " + gameInstance.getPlayersModel().get(2));
        GameModeRes gameModeRes = new GameModeRes(player1, GameMode.ADVANCED);
        gameController.onMessageReceived(gameModeRes);

        ChosenTowerMessage chosenTowerMessage = new ChosenTowerMessage(player1, tower1);
        gameController.onMessageReceived(chosenTowerMessage);
        chosenTowerMessage = new ChosenTowerMessage(player2, tower2);
        gameController.onMessageReceived(chosenTowerMessage);
        chosenTowerMessage = new ChosenTowerMessage(player3, tower3);
        gameController.onMessageReceived(chosenTowerMessage);

        assertEquals(gameInstance.getPlayersModel().get(0).getNickname(), player1);
        assertEquals(gameInstance.getPlayersModel().get(0).getColorTower(), ColorTower.BLACK);

        assertEquals(gameInstance.getPlayersModel().get(1).getNickname(), player2);
        assertEquals(gameInstance.getPlayersModel().get(1).getColorTower(), ColorTower.WHITE);

        assertEquals(gameInstance.getPlayersModel().get(2).getNickname(), player3);
        assertEquals(gameInstance.getPlayersModel().get(2).getColorTower(), ColorTower.GREY);

        for(int i=0; i<gameInstance.getPlayersModel().size(); i++)
            gameInstance.getPlayersModel().get(i).setStudentInEntrance(entrance);

        card1.setOwner(gameInstance.getPlayerByNickname(player1));
        card2.setOwner(gameInstance.getPlayerByNickname(player2));
        card3.setOwner(gameInstance.getPlayerByNickname(player3));

        gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().set(5, card1);
        gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().set(5, card2);
        gameInstance.getPlayersModel().get(2).getDeckAssistantCardModel().set(3, card3);

        assertTrue(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0).equals(ColorPawns.RED));
        assertEquals(0, gameInstance.getPlayersModel().get(0).getStudentInHall().get(ColorPawns.RED));
        assertEquals(0, gameInstance.getPlayersModel().get(2).getStudentInHall().get(ColorPawns.BLUE));
        assertEquals(4, gameInstance.getCloudsModel().get(0).getStudents().size());
        assertEquals(gameInstance.getPlayersModel().get(0), gameController.getPlayerActive());
        assertEquals(3, gameInstance.getPlayersModel().get(0).getCharacterDeck().size());
        assertEquals("ExtraMovementMotherEffect", gameInstance.getPlayersModel().get(0).getCharacterDeck().get(0).getEffect().getClass().getSimpleName());
        assertEquals("ProhibitionEffect", gameInstance.getPlayersModel().get(0).getCharacterDeck().get(1).getEffect().getClass().getSimpleName());
        assertEquals("IgnoreTowerEffect", gameInstance.getPlayersModel().get(0).getCharacterDeck().get(2).getEffect().getClass().getSimpleName());
        assertEquals(3, gameInstance.getCloudsModel().size());

        gameInstance.getPlayersModel().forEach(p -> {
            assertEquals(6, p.getTowerNumber());
            assertEquals(9, p.getStudentInEntrance().size());
        });
        assertEquals(gameInstance.getPlayersModel(), gameInstance.getPhaseOrder());
        assertEquals(10, gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().size());


        //giocano carte
        Map<PlayerModel, AssistantCardModel> cardsPlayed = new HashMap<>();
        int priority1 = gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(5).getPriority();
        int movement1 = gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(5).getMotherNatureMovement();
        AssistantCardModel after = gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(6);

        AssistantCardModel card1 = gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(5);
        cardsPlayed.put(gameInstance.getPlayersModel().get(0), card1);
        PlayAssistantCardMessage msgCardPl1 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(0).getNickname(), 5);
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(after, gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(5));
        assertEquals(1, gameInstance.getCemetery().size());

        assertEquals(priority1, gameInstance.getCemetery().get(0).getPriority());
        assertEquals(movement1, gameInstance.getCemetery().get(0).getMotherNatureMovement());

        int priority2 = gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(5).getPriority();
        int movement2 = gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(5).getMotherNatureMovement();
        after = gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(6);
        cardsPlayed.put(gameInstance.getPlayersModel().get(1), gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(5));
        PlayAssistantCardMessage msgCardPl2 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(1).getNickname(), 5);

        gameController.onMessageReceived(msgCardPl2);

        assertEquals(after, gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(5));
        assertEquals(2, gameInstance.getCemetery().size());

        assertEquals(priority2, gameInstance.getCemetery().get(1).getPriority());
        assertEquals(movement2, gameInstance.getCemetery().get(1).getMotherNatureMovement());

        cardsPlayed.put(gameInstance.getPlayersModel().get(2), gameInstance.getPlayersModel().get(2).getDeckAssistantCardModel().get(3));
        PlayAssistantCardMessage msgCardPl3 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(2).getNickname(), 3);
        gameController.onMessageReceived(msgCardPl3);

        //assertEquals(after, gameInstance.getPlayersModel().get(2).getDeckAssistantCardModel().get(3));
        assertEquals(0, gameInstance.getCemetery().size());
        /*System.out.println("carta " + player1 + " " + gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getMotherNatureMovement());
        System.out.println("carta " + player2 + " " + gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getMotherNatureMovement());
        System.out.println("carta " + player3 + " " + gameInstance.getPlayersModel(..get(2getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel(..get(2getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getMotherNatureMovement() );
        System.out.println("cimitero:");
        System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );*/

        int i = 0;
        for (AssistantCardModel cardPlayed : gameInstance.getCemetery()) {
            assertEquals(gameInstance.getPlayersModel().get(i).getNickname(), cardPlayed.getOwner().getNickname());
            i++;
        }

        /*for(PlayerModel p: gameInstance.getPhaseOrder()){
            System.out.println(p.getNickname());
        }*/

        //turno primo giocatore

        PlayedCharacterCardMessage playedCharacterCardMessage = new PlayedCharacterCardMessage(gameController.getPlayerActive().getNickname(), gameInstance.getPlayersModel().get(0).getCharacterDeck().get(0));
        gameController.onMessageReceived(playedCharacterCardMessage);
        int motherMovements = gameController.getPlayerActive().getMovementMotherNatureCurrentActionPhase();
        assertEquals(motherMovements, cardsPlayed.get(gameController.getPlayerActive()).getMotherNatureMovement() + 2);

        PlayerModel firstPlayer = gameInstance.getPhaseOrder().get(0);
        /*System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));*/
        ColorPawns student1ToMove = firstPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns student2ToMove = firstPlayer.getStudentInEntrance().get(1);
        List<ColorPawns> playersToMove = new ArrayList<>();
        playersToMove.add(student1ToMove);
        playersToMove.add(student2ToMove);
        MovedStudentOnIslandMessage msgPlayer1 = new MovedStudentOnIslandMessage(firstPlayer.getNickname(), playersToMove, 1);
        gameController.onMessageReceived(msgPlayer1);
        //assertEquals(gameController.getPlayerActive().getNickname(), firstPlayer.getNickname());

        //gameInstance.getIslandsModel().get(1).getStudents().forEach(System.out::println);
        assertTrue(gameInstance.getIslandsModel().get(1).getStudents().containsAll(playersToMove));

        ColorPawns color = firstPlayer.getStudentInEntrance().get(0);
        ColorPawns color2;
        assertEquals(firstPlayer, gameController.getPlayerActive());
        MovedStudentToHallMessage toHallMessage = new MovedStudentToHallMessage(gameController.getPlayerActive().getNickname(), List.of(color));
        gameController.onMessageReceived(toHallMessage);
        assertEquals(1, firstPlayer.getStudentInHall().get(color));



        int indexOldMother = gameInstance.getMotherNatureIndex();

        MovedMotherNatureMessage motherNatureMessage = new MovedMotherNatureMessage(firstPlayer.getNickname(), (byte) 1);
        gameController.onMessageReceived(motherNatureMessage);

        assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother + 1) % 12);



        /*System.out.println(gameInstance.getCloudsModel().get(0).getStudents());
        System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
        System.out.println(gameInstance.getCloudsModel().get(2).getStudents());*/
        CloudModel cloud = gameInstance.getCloudsModel().get(2);
        AddStudentFromCloudToEntranceMessage msgCloudToWaiting = new AddStudentFromCloudToEntranceMessage(firstPlayer.getNickname(), 1);
        gameController.onMessageReceived(msgCloudToWaiting);
        //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
        assertEquals(cloud, gameInstance.getCloudsModel().get(1));

        //turno secondo giocatore
        PlayerModel secondPlayer = gameInstance.getPlayersModel().get(1);
        /*System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
        System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));*/
        ColorPawns secondStudent1ToMove = secondPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns secondStudent2ToMove = secondPlayer.getStudentInEntrance().get(1);
        ColorPawns secondStudent3ToMove = secondPlayer.getStudentInEntrance().get(2);
        List<ColorPawns> secondStudentsToMove = new ArrayList<>();
        secondStudentsToMove.add(secondStudent1ToMove);
        secondStudentsToMove.add(secondStudent2ToMove);
        secondStudentsToMove.add(secondStudent3ToMove);
        MovedStudentOnIslandMessage msgPlayer2 = new MovedStudentOnIslandMessage(secondPlayer.getNickname(), secondStudentsToMove, 5);
        gameController.onMessageReceived(msgPlayer2);
        assertEquals(gameController.getPlayerActive().getNickname(), secondPlayer.getNickname());

        //gameInstance.getIslandsModel().get(1).getStudents().forEach(System.out::println);
        assertTrue(gameInstance.getIslandsModel().get(5).getStudents().containsAll(secondStudentsToMove));

        color2 = secondPlayer.getStudentInEntrance().get(0);
        assertEquals(secondPlayer, gameController.getPlayerActive());

        gameInstance.getPlayersModel().get(1).addCoins();
        playedCharacterCardMessage = new PlayedCharacterCardMessage(gameInstance.getPlayersModel().get(1).getNickname(), gameInstance.getPlayersModel().get(1).getCharacterDeck().get(1));
        gameController.onMessageReceived(playedCharacterCardMessage);
        MovedBanCardMessage movedBanCardMessage = new MovedBanCardMessage(gameInstance.getPlayersModel().get(1).getNickname(), 4);
        gameController.onMessageReceived(movedBanCardMessage);
        assertTrue(gameInstance.getIslandsModel().get(4).hasProhibition());

        indexOldMother = gameInstance.getMotherNatureIndex();
        MovedMotherNatureMessage motherNatureMessage2 = new MovedMotherNatureMessage(secondPlayer.getNickname(), (byte) 1);
        gameController.onMessageReceived(motherNatureMessage2);

        AddStudentFromCloudToEntranceMessage msgCloudToWaiting2 = new AddStudentFromCloudToEntranceMessage(secondPlayer.getNickname(), 0);
        cloud = gameInstance.getCloudsModel().get(1);
        gameController.onMessageReceived(msgCloudToWaiting2);
        assertEquals(cloud, gameInstance.getCloudsModel().get(0));

        //turno terzo giocatore
        playedCharacterCardMessage = new PlayedCharacterCardMessage(gameController.getPlayerActive().getNickname(), gameController.getPlayerActive().getCharacterDeck().get(2));
        gameController.onMessageReceived(playedCharacterCardMessage);
        assertFalse(gameController.getConsiderTower());
        PlayerModel thirdPlayer = gameInstance.getPhaseOrder().get(2);
        ColorPawns thirdStudent1ToMove = thirdPlayer.getStudentInEntrance().get(0); //to island
        ColorPawns thirdStudent2ToMove = thirdPlayer.getStudentInEntrance().get(1);
        List<ColorPawns> thirdStudentsToMove = new ArrayList<>();
        thirdStudentsToMove.add(thirdStudent1ToMove);
        thirdStudentsToMove.add(thirdStudent2ToMove);

        MovedStudentOnIslandMessage msgPlayer3 = new MovedStudentOnIslandMessage(thirdPlayer.getNickname(), thirdStudentsToMove, 8);
        gameController.onMessageReceived(msgPlayer3);
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
        assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother+1)%gameInstance.getIslandsModel().size());

        AddStudentFromCloudToEntranceMessage msgCloudToWaiting3 = new AddStudentFromCloudToEntranceMessage(thirdPlayer.getNickname(), 0);
        gameController.onMessageReceived(msgCloudToWaiting3);
        //System.out.println(gameInstance.getCloudsModel().get(0).getStudents());
        //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
        //System.out.println(gameInstance.getCloudsModel().get(2).getStudents());
        assertEquals(4, gameInstance.getCloudsModel().get(2).getStudents().size());


        assertEquals(9, gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().size());

        card1 = gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(2);
        msgCardPl1 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(0).getNickname(), 2);
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(card1, gameInstance.getCemetery().get(0));
        assertEquals(1, gameInstance.getCemetery().size());

        AssistantCardModel card2 = gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(7);
        msgCardPl2 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(1).getNickname(), 7);
        gameController.onMessageReceived(msgCardPl2);

        assertEquals(card2, gameInstance.getCemetery().get(1));
        assertEquals(2, gameInstance.getCemetery().size());


        msgCardPl3 = new PlayAssistantCardMessage(gameInstance.getPlayersModel().get(2).getNickname(), 5);
        AssistantCardModel card3 = gameInstance.getPlayersModel().get(2).getDeckAssistantCardModel().get(5);
        gameController.onMessageReceived(msgCardPl3);
        assertEquals(0, gameInstance.getCemetery().size());


        gameInstance.getIslandsModel().get(5).setTowerColor(ColorTower.GREY);
        gameInstance.getIslandsModel().get(6).setTowerColor(ColorTower.GREY);
        gameInstance.getIslandsModel().get(5).setMotherNature(true);
        gameController.computeIslandsChanges(gameController.getPlayerActive(), gameInstance.getIslandsModel().get(5));

        /*System.out.println("carta " + player1 + " " + gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().get(msgCardPl1.getIndexCard()).getMotherNatureMovement() );
        System.out.println("carta " + player2 + " " + gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel().get(1).getDeckAssistantCardModel().get(msgCardPl2.getIndexCard()).getMotherNatureMovement() );
        System.out.println("carta " + player3 + " " + gameInstance.getPlayersModel(..get(2getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getPriority() + ", "+ gameInstance.getPlayersModel(..get(2getDeckAssistantCardModel().get(msgCardPl3.getIndexCard()).getMotherNatureMovement() );
        System.out.println("cimitero:");
        System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
        System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );*/



        /*for(PlayerModel p: gameInstance.getPhaseOrder()){
            System.out.println(p.getNickname());
        }*/
    }
}