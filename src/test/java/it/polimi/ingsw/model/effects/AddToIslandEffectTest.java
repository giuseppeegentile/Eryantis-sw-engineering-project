package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddToIslandEffectTest {

    @Test
    void addToIsland(){
        List<ColorPawns> students = new ArrayList<>(asList(ColorPawns.RED));
        List<IslandModel> islands = new ArrayList<>(asList(new IslandModel(true, new ArrayList<>()), new IslandModel(false, new ArrayList<>())));
        GameModel.getInstance().setIslands(islands);
        GameModel.getInstance().setBag(students);
        List<ColorPawns> list = new ArrayList<>(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED));
        AddToIslandEffect effect = new AddToIslandEffect(list);
        assertEquals(asList(ColorPawns.RED, ColorPawns.RED, ColorPawns.RED, ColorPawns.RED), effect.getStudents());
        effect.choose(ColorPawns.RED, 1);
        effect.enable(new PlayerModel("Joice"));
        effect.incrementCost();
        assertEquals(List.of(ColorPawns.RED),GameModel.getInstance().getIslandsModel().get(1).getStudents());
        assertEquals(2, effect.getCostForEffect());
        assertEquals(ColorPawns.RED, effect.getStudentChosen());
        assertEquals(GameModel.getInstance().getIslandsModel().get(1), effect.getIslandChosen());
        assertEquals(2, effect.getCoinsForEffect());

        assertEquals("At the start of the match, take " + effect.getNumStudents() + " students and place them on this card.\n" +
                "                                  EFFECT: take 1 student from this card and place it on an island you chose. Then, draw a student from the bag and place it on this card.", effect.getDescription());
    }

}