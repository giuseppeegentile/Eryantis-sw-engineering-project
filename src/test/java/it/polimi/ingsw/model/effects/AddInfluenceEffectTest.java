package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddInfluenceEffectTest {

    @Test
    void AddInfluence(){
        AddInfluenceEffect effect = new AddInfluenceEffect(new GameController());
        effect.enable(new PlayerModel("Chrissy"));
        assertEquals(3, effect.getCoinsForEffect());
        assertEquals("EFFECT: this turn, during the influence calculation you have 2 additional influence points.", effect.getDescription());
    }

}