package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PickIslandInfluenceEffectTest {

    @Test
    void pickIslandInfluenceEffect(){
        GameController gameController = new GameController();
        List<IslandModel> island = new ArrayList<>();
        island.add(new IslandModel(true, ColorPawns.PINK));
        GameModel.getInstance().setIslands(island);
        PickIslandInfluenceEffect effect = new PickIslandInfluenceEffect(gameController);
        effect.choose(0);
        effect.enable(new PlayerModel("Giannni"));
        effect.incrementCost();
        assertEquals(4, effect.getCoinsForEffect());
        assertEquals("EFFECT: choose an island and calculate the majority as if mother nature ended her movement there. In this turn, mother nature will move as usual and on the island where her movement ends, the majority will normally be calculated.", effect.getDescription());
        assertEquals(0, effect.getIndexIslandEffect());
        effect.setIndexIslandEffect(3);
        assertEquals(3, effect.getIndexIslandEffect());
    }

}