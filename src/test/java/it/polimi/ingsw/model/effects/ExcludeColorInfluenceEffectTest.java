package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcludeColorInfluenceEffectTest {

    @Test
    void excludeColorInfluenceEffect(){
        GameController gameController = new GameController();
        ExcludeColorInfluenceEffect effect = new ExcludeColorInfluenceEffect(gameController);
        effect.chose(ColorPawns.RED);
        effect.enable(new PlayerModel("Mindflayer"));
        assertEquals(4, effect.getCoinsForEffect());
        assertEquals(null, effect.getDescription());
        assertEquals(ColorPawns.RED, effect.getColorToExclude());
    }
}