package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IgnoreTowerEffectTest {

    @Test
    void ignoreTowerEffect(){
        GameController gameController = new GameController();
        IgnoreTowerEffect effect = new IgnoreTowerEffect(gameController);
        effect.enable(new PlayerModel("Victor Creel"));
        effect.incrementCost();
        assertFalse(gameController.getConsiderTower());
        assertEquals(4, effect.getCoinsForEffect());
        assertEquals("EFFECT: when counting the influence on an island (or a group of islands), the towers present are not calculated.", effect.getDescription());
    }
}