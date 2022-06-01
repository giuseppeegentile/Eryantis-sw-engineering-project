package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddToHallEffectTest {

    @Test
    void AddToHallEffect(){
        List<ColorPawns> list = new ArrayList<>(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED));
        List<ColorPawns> list2 = new ArrayList<>(asList(ColorPawns.RED));
        GameModel.getInstance().setBag(list2);
        AddToHallEffect effect = new AddToHallEffect(list);
        effect.choose(2);
        PlayerModel player = new PlayerModel("Rafael Nadal");
        effect.enable(player);
        assertEquals(1, player.getStudentInHall().get(ColorPawns.RED));
        assertEquals(3, effect.getCoinsForEffect());
        assertEquals("At the start of the match, take " + effect.getNumStudents() + " students and place them on this card.\n" +
                "EFFECT:  Take 1 student from this card and place it in your hall. Draw then a new student and place it on this card.", effect.getDescription());
    }

}