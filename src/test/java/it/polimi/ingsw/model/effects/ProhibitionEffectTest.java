package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProhibitionEffectTest {

    @Test
    void ProhibitionEffect(){
        List<IslandModel> island = new ArrayList<>();
        island.add(new IslandModel(true, ColorPawns.PINK));
        GameModel.getInstance().setIslands(island);
        ProhibitionEffect effect = new ProhibitionEffect();
        effect.choose(0);
        effect.enable(new PlayerModel("Ajeje"));
        assertEquals(3, effect.getNumberProhibition());
        assertEquals(3, effect.getCoinsForEffect());
        assertTrue(GameModel.getInstance().getIslandsModel().get(0).hasProhibition());
        effect.endEffect();
        assertEquals(4, effect.getNumberProhibition());
        assertFalse(GameModel.getInstance().getIslandsModel().get(0).hasProhibition());
        assertEquals("At the beginning of the game, place the 4 prohibition tiles on this card.\n" +
                "EFFECT: place a ban tile on an island of your choice. the first time mother nature ends her movement there, put the prohibition tile back on the card without calculating the influence on that island or placing towers.", effect.getDescription());
    }
}