package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StartGameStateTest {
    GameModel gameModel = GameModel.getInstance();
    StartGameState ssg= new StartGameState(gameModel);

    @Test
    @Order(1)
    void initialConfigurationIslandSize() {
        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide");
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian");
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe");
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto");
        playersModels.add(player4);

        List<ColorTower> colorTowers = new ArrayList<>(Arrays.asList(ColorTower.BLACK, ColorTower.WHITE, ColorTower.BLACK, ColorTower.WHITE));
        GameMode princ = GameMode.PRINCIPIANTE;

        ssg.setInitialGameConfiguration(playersModels, colorTowers, princ);
        assertEquals(12, gameModel.getIslandsModel().size());
        AtomicInteger numNoMother = new AtomicInteger(0);
        AtomicInteger numYesMother = new AtomicInteger(0);
        AtomicInteger numNoStudent = new AtomicInteger(0);
        gameModel.getIslandsModel().forEach(island -> {
            if(!island.getMotherNature()) numNoMother.getAndIncrement();
            else numYesMother.getAndIncrement();
            if(island.getStudents().size() == 0) numNoStudent.getAndIncrement();
        });
        assertEquals(1,numYesMother.get());
        assertEquals(11,numNoMother.get());
        assertEquals(2,numNoStudent.get());
        gameModel.getIslandsModel().get(0).setTowerColor(ColorTower.BLACK);
        assertEquals(ColorTower.BLACK,gameModel.getIslandsModel().get(0).getTowerColor());
    }


    //funziona ma non si può verificare.. genera dei valori casuali
    @Test
    void fillListWithColors(){
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        colors = ssg.fillListWithColors(colors, 10, 2);
        /*colors.forEach(c->{
            System.out.println(c.toString());
        });*/
    }


    @Test
    @Order(2)
    void testGenerationOfDeck(){
        assertEquals(40, gameModel.getDeck().size());
        int countPriorityEven = (int) gameModel.getDeck().stream().filter(c -> c.getPriority() % 2 == 0).count();
        int countPriorityOdd = (int) gameModel.getDeck().stream().filter(c -> c.getPriority() % 2 != 0).count();
        int countMovementFive = (int) gameModel.getDeck().stream().filter(c -> c.getMotherNatureMovement() == 5).count();
        int countMovementFour = (int) gameModel.getDeck().stream().filter(c -> c.getMotherNatureMovement() == 4).count();
        int countMovementThree = (int) gameModel.getDeck().stream().filter(c -> c.getMotherNatureMovement() == 3).count();
        int countMovementTwo = (int) gameModel.getDeck().stream().filter(c -> c.getMotherNatureMovement() == 2).count();
        int countMovementone = (int) gameModel.getDeck().stream().filter(c -> c.getMotherNatureMovement() == 1).count();
        assertEquals(8, countMovementFive);  //ci sono 8 valori di madre natura di 5
        assertEquals(8, countMovementThree);//ci sono 8 valori di madre natura di 3
        assertEquals(8, countMovementFour);//ci sono 8 valori di madre natura di 4
        assertEquals(8, countMovementTwo);//ci sono 8 valori di madre natura di 2
        assertEquals(8, countMovementone);//ci sono 8 valori di madre natura di 1


        assertEquals(20, countPriorityOdd); //ci sono 20 numeri dispari di priorità
        assertEquals(20, countPriorityEven);//ci sono 20 numeri pari di priorità

        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger j = new AtomicInteger(1);
        //non posso testarlo perchè è randomico
        gameModel.getDeck().forEach(c ->{
            //System.out.println("Priorità: " +  c.getPriority() + ", movimento mn: " + c.getMotherNatureMovement());
            //System.out.println(i.get() + "  " +j.get() );
            if(i.get() %2 == 0) j.getAndIncrement();
            i.getAndIncrement();
            if(i.get() == 11){
                i.set(1);
                j.set(1);
            }
        });
    }

    @Test
    @Order(3)
    void testAssignmentDeckToPlayers(){
        assertEquals(4, gameModel.getPlayersModel().size());
        gameModel.getPlayersModel().forEach(p ->{
            p.getDeckAssistantCardModel().forEach(c ->{
                assertEquals(p, c.getOwner());
            });
            assertEquals(10,p.getDeckAssistantCardModel().size());
        });
    }
}