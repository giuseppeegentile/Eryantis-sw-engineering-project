package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PickIslandInfluenceEffectTest {

    @Test
    void pickIslandInfluenceEffect(){
        GameController gameController = new GameController();
        List<IslandModel> island = new ArrayList<>();
        island.add(new IslandModel(true, ColorPawns.PINK));
        gameController.getGameInstance().setIslands(island);
        PickIslandInfluenceEffect effect = new PickIslandInfluenceEffect(gameController);
        effect.chose(0);
        effect.enable(new PlayerModel("Giannni"));
        assertEquals(4, effect.getCoinsForEffect());
        assertNull(effect.getDescription());
        assertEquals(0, effect.getIndexIslandEffect());
        effect.setIndexIslandEffect(3);
        assertEquals(3, effect.getIndexIslandEffect());
    }

}