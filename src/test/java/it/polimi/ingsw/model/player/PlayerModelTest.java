package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.effects.ControlProfEffect;
import it.polimi.ingsw.model.game.GameModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.colors.ColorPawns.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class PlayerModelTest {
    static GameModel testGame = GameModel.getInstance();

    @Test
    @BeforeAll
    static void init() {
        testGame.endGame();
    }

    @Test
    void playerModelTest(){
        PlayerModel player = new PlayerModel("Giacomo Poretti", ColorTower.GREY);
        List<ColorPawns> students = new ArrayList<>(asList(ColorPawns.RED, ColorPawns.PINK, ColorPawns.GREEN));
        player.addStudentsHall(students);
        player.addStudentsEntrance(students);
        player.removeStudentFromEntrance(ColorPawns.RED);
        assertTrue(player.getStudentInEntrance().containsAll(asList(ColorPawns.PINK, ColorPawns.GREEN)));
        player.addProf(ColorPawns.GREEN);
        assertEquals(1, player.getNumProfs());
        assertEquals(List.of(GREEN), player.getProfs());
        assertTrue(player.hasProf(ColorPawns.GREEN));
        player.removeProf(ColorPawns.GREEN);
        assertFalse(player.hasProf(ColorPawns.GREEN));
        assertEquals("Giacomo Poretti", player.getNickname());
        player.setNickname("Giovanni Storti");
        assertEquals("Giovanni Storti", player.getNickname());
        player.removeStudentFromEntrance(asList(ColorPawns.PINK, ColorPawns.GREEN));
        assertTrue(player.getStudentInEntrance().isEmpty());
        player.setCoins();
        player.addCoins();
        player.addCoins();
        player.removeCoins(1);
        assertEquals(2, player.getCoins());
        Map<ColorPawns, Integer> studentsInHall = new HashMap<>( Map.of(
                GREEN, 0,
                RED, 1,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
        player.setStudentHall(studentsInHall);
        assertEquals(studentsInHall, player.getStudentInHall());
        List<ColorPawns> newStudents = new ArrayList<>();
        newStudents.add(ColorPawns.RED);
        player.removeStudentFromHall(newStudents);
        //assertTrue(player.getStudentInHall().isEmpty());
        player.setTowers(ColorTower.GREY, 5);
        assertEquals(5, player.getTowerNumber());
        player.setMovementMotherNatureCurrentActionPhase((byte)6);
        assertEquals((byte)6, player.getMovementMotherNatureCurrentActionPhase());
        List<AssistantCardModel> card = new ArrayList<>(List.of(new AssistantCardModel(4, (byte) 5)));
        player.setDeckAssistantCardModel(card);
        assertEquals(card, player.getDeckAssistantCardModel());
        assertEquals(ColorTower.GREY, player.getColorTower());
        player.setStudentInEntrance(List.of(PINK));
        assertEquals(List.of(PINK), player.getStudentInEntrance());
        player.addTowerToBoard();
        player.addTowerToBoard();
        player.removeTowerFromBoard();
        assertEquals(6, player.getTowerNumber());
        PlayerModel playerSame;
        playerSame = player;
        assertEquals(player, playerSame);
        assertEquals(1786650663, player.hashCode());
        List<CharacterCardModel> characterCard = new ArrayList<>();
        CharacterCardModel cards = new CharacterCardModel(new ControlProfEffect(), 1);
        characterCard.add(cards);
        player.assignCharacterDeck(characterCard);
        assertEquals(characterCard, player.getCharacterDeck());
    }
}