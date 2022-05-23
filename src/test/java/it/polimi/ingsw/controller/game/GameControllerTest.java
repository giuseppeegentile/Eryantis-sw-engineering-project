package it.polimi.ingsw.controller.game;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    @Order(1)
    void testingGameTurn() {
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
        PlayAssistantCardMessage msgCardPl1 = new PlayAssistantCardMessage(player1, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5));
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(5).getMotherNatureMovement());
        assertEquals(1, gameInstance.getCemetery().size());

        assertEquals(priority1, gameInstance.getCemetery().get(0).getPriority());
        assertEquals(movement1, gameInstance.getCemetery().get(0).getMotherNatureMovement());

        int priority2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getPriority();
        int movement2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getMotherNatureMovement();
        PlayAssistantCardMessage msgCardPl2 = new PlayAssistantCardMessage(player2, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5));
        gameController.onMessageReceived(msgCardPl2);
        if (gameController.isBoolForTestPlayedCard()) {
            assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getPriority());
            assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(5).getMotherNatureMovement());
            assertEquals(2, gameInstance.getCemetery().size());

            assertEquals(priority2, gameInstance.getCemetery().get(1).getPriority());
            assertEquals(movement2, gameInstance.getCemetery().get(1).getMotherNatureMovement());


            int priority3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getPriority();
            int movement3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getMotherNatureMovement();
            PlayAssistantCardMessage msgCardPl3 = new PlayAssistantCardMessage(player3, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3));
            gameController.onMessageReceived(msgCardPl3);
            if (gameController.isBoolForTestPlayedCard()) {
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getPriority());
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(3).getMotherNatureMovement());
                assertEquals(3, gameInstance.getCemetery().size());
/*
                System.out.println("carta " + player1 + " " + msgCardPl1.getCard().getPriority() + ", "+ msgCardPl1.getCard().getMotherNatureMovement() );
                System.out.println("carta " + player2 + " " + msgCardPl2.getCard().getPriority() + ", "+ msgCardPl2.getCard().getMotherNatureMovement() );
                System.out.println("carta " + player3 + " " + msgCardPl3.getCard().getPriority() + ", "+ msgCardPl3.getCard().getMotherNatureMovement() );
                System.out.println("cimitero:");
                System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
                System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
                System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );*/


                assertEquals(priority3, gameInstance.getCemetery().get(2).getPriority());
                assertEquals(movement3, gameInstance.getCemetery().get(2).getMotherNatureMovement());
                assertEquals(PhaseGame.ADD_STUDENT_TO_ISLAND, gameController.getPhaseGame());

                int i = 0;
                for (AssistantCardModel cardPlayed : gameInstance.getCemetery()) {
                    assertEquals(gameInstance.getPlayersModel().get(i).getNickname(), cardPlayed.getOwner().getNickname());
                    i++;
                }

/*                for(PlayerModel p: gameInstance.getPhaseOrder()){
                    System.out.println(p.getNickname());
                }*/
            } else {
                int priority4 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(6).getPriority();
                int movement4 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(6).getMotherNatureMovement();
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(6).getPriority());
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(6).getMotherNatureMovement());
                assertEquals(3, gameInstance.getCemetery().size());

                assertEquals(priority4, gameInstance.getCemetery().get(2).getPriority());
                assertEquals(movement4, gameInstance.getCemetery().get(2).getMotherNatureMovement());
                assertEquals(PhaseGame.ADD_STUDENT_TO_ISLAND, gameController.getPhaseGame());

                int i = 0;
                for (AssistantCardModel cardPlayed : gameInstance.getCemetery()) {
                    assertEquals(gameInstance.getPlayersModel().get(i).getNickname(), cardPlayed.getOwner().getNickname());
                    i++;
                }
            }

            //turno primo giocatore

            PlayerModel firstPlayer = gameInstance.getPhaseOrder().get(0);
/*          System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
            System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
            System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));*/
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

            assertEquals(PhaseGame.ADD_STUDENT_TO_HALL, gameController.getPhaseGame());
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

            assertEquals(PhaseGame.MOVE_MOTHER, gameController.getPhaseGame());


            int indexOldMother = gameInstance.getMotherNatureIndex();

            MovedMotherNatureMessage motherNatureMessage = new MovedMotherNatureMessage(firstPlayer.getNickname(), (byte) 1);
            gameController.onMessageReceived(motherNatureMessage);

            assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother + 1) % 11);


            assertEquals(PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE, gameController.getPhaseGame());

            AddStudentFromCloudToEntranceMessage msgCloudToWaiting = new AddStudentFromCloudToEntranceMessage(firstPlayer.getNickname(), 1);
            gameController.onMessageReceived(msgCloudToWaiting);
            //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
            assertEquals(0, gameInstance.getCloudsModel().get(1).getStudents().size());

            //turno secondo giocatore
            PlayerModel secondPlayer = gameInstance.getPhaseOrder().get(1);
/*          System.out.println(gameInstance.getIslandsModel().get(1).getStudents());
            System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(0));
            System.out.println(gameInstance.getPlayersModel().get(0).getStudentInEntrance().get(1));*/
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

            assertEquals(PhaseGame.ADD_STUDENT_TO_HALL, gameController.getPhaseGame());
            color2 = secondPlayer.getStudentInEntrance().get(0);

            assertEquals(secondPlayer, gameController.getPlayerActive());
            MovedStudentToHallMessage toHallMessage2 = new MovedStudentToHallMessage(gameController.getPlayerActive().getNickname(), List.of(color2));
            gameController.onMessageReceived(toHallMessage2);
            assertEquals(1, secondPlayer.getStudentInHall().get(color2));

            assertEquals(PhaseGame.MOVE_MOTHER, gameController.getPhaseGame());

            indexOldMother = gameInstance.getMotherNatureIndex();
            MovedMotherNatureMessage motherNatureMessage2 = new MovedMotherNatureMessage(secondPlayer.getNickname(), (byte) 1);
            gameController.onMessageReceived(motherNatureMessage2);

            assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother+1)%11);
            assertEquals(PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE, gameController.getPhaseGame());

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

            assertEquals(PhaseGame.ADD_STUDENT_TO_HALL, gameController.getPhaseGame());
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

            assertEquals(PhaseGame.MOVE_MOTHER, gameController.getPhaseGame());

            indexOldMother = gameInstance.getMotherNatureIndex();
            MovedMotherNatureMessage motherNatureMessage3 = new MovedMotherNatureMessage(thirdPlayer.getNickname(), (byte) 1);
            gameController.onMessageReceived(motherNatureMessage3);
            assertEquals(gameInstance.getMotherNatureIndex(), (indexOldMother+1)%11);
            assertEquals(PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE, gameController.getPhaseGame());

            AddStudentFromCloudToEntranceMessage msgCloudToWaiting3 = new AddStudentFromCloudToEntranceMessage(thirdPlayer.getNickname(), 2);
            gameController.onMessageReceived(msgCloudToWaiting3);
            //System.out.println(gameInstance.getCloudsModel().get(0).getStudents());
            //System.out.println(gameInstance.getCloudsModel().get(1).getStudents());
            //System.out.println(gameInstance.getCloudsModel().get(2).getStudents());
            assertEquals(4, gameInstance.getCloudsModel().get(2).getStudents().size());
        }

        assertEquals(10, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().size());

        priority1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getPriority();
        movement1 = gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getMotherNatureMovement();
        msgCardPl1 = new PlayAssistantCardMessage(player1, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2));
        gameController.onMessageReceived(msgCardPl1);
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getPriority());
        assertEquals(0, gameInstance.getPlayerByNickname(player1).getDeckAssistantCardModel().get(2).getMotherNatureMovement());
        assertEquals(1, gameInstance.getCemetery().size());

        assertEquals(priority1, gameInstance.getCemetery().get(0).getPriority());
        assertEquals(movement1, gameInstance.getCemetery().get(0).getMotherNatureMovement());

        priority2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getPriority();
        movement2 = gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getMotherNatureMovement();
        msgCardPl2 = new PlayAssistantCardMessage(player2, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7));
        gameController.onMessageReceived(msgCardPl2);
        if (gameController.isBoolForTestPlayedCard()) {
            assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getPriority());
            assertEquals(0, gameInstance.getPlayerByNickname(player2).getDeckAssistantCardModel().get(7).getMotherNatureMovement());
            assertEquals(2, gameInstance.getCemetery().size());

            assertEquals(priority2, gameInstance.getCemetery().get(1).getPriority());
            assertEquals(movement2, gameInstance.getCemetery().get(1).getMotherNatureMovement());


            int priority3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getPriority();
            int movement3 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getMotherNatureMovement();
            PlayAssistantCardMessage msgCardPl3 = new PlayAssistantCardMessage(player3, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8));
            //qualcosa non va qui
            gameController.onMessageReceived(msgCardPl3);
            if (gameController.isBoolForTestPlayedCard()) {
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getPriority());
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(8).getMotherNatureMovement());
                assertEquals(3, gameInstance.getCemetery().size());
/*
                System.out.println("carta " + player1 + " " + msgCardPl1.getCard().getPriority() + ", "+ msgCardPl1.getCard().getMotherNatureMovement() );
                System.out.println("carta " + player2 + " " + msgCardPl2.getCard().getPriority() + ", "+ msgCardPl2.getCard().getMotherNatureMovement() );
                System.out.println("carta " + player3 + " " + msgCardPl3.getCard().getPriority() + ", "+ msgCardPl3.getCard().getMotherNatureMovement() );
                System.out.println("cimitero:");
                System.out.println(gameInstance.getCemetery().get(0).getPriority() + ", " +gameInstance.getCemetery().get(0).getMotherNatureMovement() );
                System.out.println(gameInstance.getCemetery().get(1).getPriority() + ", " +gameInstance.getCemetery().get(1).getMotherNatureMovement() );
                System.out.println(gameInstance.getCemetery().get(2).getPriority() + ", " +gameInstance.getCemetery().get(2).getMotherNatureMovement() );*/


                assertEquals(priority3, gameInstance.getCemetery().get(2).getPriority());
                assertEquals(movement3, gameInstance.getCemetery().get(2).getMotherNatureMovement());
                assertEquals(PhaseGame.ADD_STUDENT_TO_ISLAND, gameController.getPhaseGame());


                /*
                for(PlayerModel p: gameInstance.getPhaseOrder()){
                    System.out.println(p.getNickname());
                }*/
            } else {
                int priority4 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(1).getPriority();
                int movement4 = gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(1).getMotherNatureMovement();
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(1).getPriority());
                assertEquals(0, gameInstance.getPlayerByNickname(player3).getDeckAssistantCardModel().get(1).getMotherNatureMovement());
                assertEquals(3, gameInstance.getCemetery().size());

                assertEquals(priority4, gameInstance.getCemetery().get(2).getPriority());
                assertEquals(movement4, gameInstance.getCemetery().get(2).getMotherNatureMovement());
                assertEquals(PhaseGame.ADD_STUDENT_TO_ISLAND, gameController.getPhaseGame());

                int i = 0;
                for (AssistantCardModel cardPlayed : gameInstance.getCemetery()) {
                    assertEquals(gameInstance.getPhaseOrder().get(i).getNickname(), cardPlayed.getOwner().getNickname());
                    i++;
                }
            }
        }
    }
}