package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.colors.ColorPawns.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeConfigEntranceEffectTest {

    @Test
    void exchangeConfigEntranceEffect(){
        List<ColorPawns> students = new ArrayList<>(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE));
        ExchangeConfigEntranceEffect effect = new ExchangeConfigEntranceEffect(students);
        effect.choose(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE), asList(ColorPawns.PINK, ColorPawns.PINK,ColorPawns.PINK));
        PlayerModel player = new PlayerModel();
        Map<ColorPawns, Integer> studentInHall =new HashMap<>( Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 3,
                BLUE, 0
        ));
        player.setStudentHall(studentInHall);
        effect.enable(player);
        Map<ColorPawns, Integer> studentInHall1 =new HashMap<>( Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 3
        ));
        assertEquals(studentInHall1, player.getStudentInHall());
        assertTrue(effect.getStudents().containsAll(asList(ColorPawns.PINK, ColorPawns.PINK, ColorPawns.PINK, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE)));
        assertEquals(2, effect.getCoinsForEffect());
        assertEquals(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE), effect.getStudentFromCard());
        assertEquals(asList(ColorPawns.PINK, ColorPawns.PINK,ColorPawns.PINK), effect.getStudentFromEntrance());
        assertEquals("At the start of the match, take " + effect.getNumStudents() + " students and place them on this card.\n" +
                "EFFECT: You can take up to 3 students from this card and exchange them with students on your hall.", effect.getDescription());
    }
}