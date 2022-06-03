package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitialConfigEffectTest {

    @Test
    void InitialConfig() {
        List<ColorPawns> list = new ArrayList<>();
        list.add(ColorPawns.RED);
        list.add(ColorPawns.RED);
        GameModel.getInstance().setBag(list);

        InitialConfigEffect initialEffect = new InitialConfigEffect(list, 1, 2);
        assertEquals(2, initialEffect.getNumStudents());
        assertEquals(list, initialEffect.getStudents());

        initialEffect.getFromBag();
        //assertEquals(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED), initialEffect.getStudents());
        //assertEquals(List.of(ColorPawns.RED) ,GameModel.getInstance().getBag());
        assertEquals(1, initialEffect.getCostForEffect());
        assertEquals("At the start of the match, take " + initialEffect.getNumStudents() + " students and place them on this card.\n" +
                "EFFECT: ", initialEffect.getDescription());
        initialEffect.setCostForEffect(4);
        assertEquals(4, initialEffect.getCostForEffect());
    }
}