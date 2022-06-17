package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExchangeConfigEntranceEffectTest {

    @Test
    void exchangeConfigEntranceEffect(){
        List<ColorPawns> students = new ArrayList<>(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE));
        ExchangeConfigEntranceEffect effect = new ExchangeConfigEntranceEffect(students);
        effect.choose(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE), asList(ColorPawns.PINK, ColorPawns.PINK,ColorPawns.PINK));
        PlayerModel player = new PlayerModel();
        List<ColorPawns> studentInEntrance =new ArrayList<>(asList(ColorPawns.PINK, ColorPawns.PINK,ColorPawns.PINK));
        player.setStudentInEntrance(studentInEntrance);
        effect.enable(player);
        effect.incrementCost();
        List<ColorPawns> studentInEntrance1 = new ArrayList<>(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE));
        assertEquals(studentInEntrance1, player.getStudentInEntrance());
        assertTrue(effect.getStudents().containsAll(asList(ColorPawns.PINK, ColorPawns.PINK, ColorPawns.PINK, ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE)));
        assertEquals(2, effect.getCoinsForEffect());
        assertEquals(asList(ColorPawns.BLUE, ColorPawns.BLUE, ColorPawns.BLUE), effect.getStudentFromCard());
        assertEquals(asList(ColorPawns.PINK, ColorPawns.PINK,ColorPawns.PINK), effect.getStudentFromEntrance());
        assertEquals("At the start of the match, take " + effect.getNumStudents() + " students and place them on this card.\n" +
                "                                  EFFECT: you can take up to 3 students from this card and exchange them with students in your entrance.", effect.getDescription());
    }
}