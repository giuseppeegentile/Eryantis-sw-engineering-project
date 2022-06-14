package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.colors.ColorPawns.*;
import static it.polimi.ingsw.model.colors.ColorPawns.BLUE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeHallEntranceEffectTest {

    @Test
    void exchangeHallEntranceEffect() {
        List<ColorPawns> studentsFromEntrance = new ArrayList<>(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE));
        List<ColorPawns> studentsFromHall = new ArrayList<>(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED));
        ExchangeHallEntranceEffect effect = new ExchangeHallEntranceEffect();
        effect.choose(studentsFromEntrance, studentsFromHall);
        PlayerModel player = new PlayerModel();
        Map<ColorPawns, Integer> studentInHall = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 3,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));
        player.setStudentHall(studentInHall);
        player.setStudentInEntrance(studentsFromEntrance);
        effect.enable(player);
        effect.incrementCost();
        Map<ColorPawns, Integer> studentInHall1 = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 3
        ));
        assertEquals(studentInHall1, player.getStudentInHall());
        assertEquals(studentsFromHall, player.getStudentInEntrance());
        assertEquals(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE), effect.getStudentFromEntrance());
        assertEquals(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED), effect.getStudentFromHall());
        assertEquals("EFFECT: you can exchange up to 2 students present in your entrance and in your hall.", effect.getDescription());
    }
}