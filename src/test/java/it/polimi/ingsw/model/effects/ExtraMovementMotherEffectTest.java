package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraMovementMotherEffectTest {

    @Test
    void ExtraMovementMotherEffect(){
        ExtraMovementMotherEffect effect = new ExtraMovementMotherEffect();
        PlayerModel player = new PlayerModel("Vecna");
        player.setMovementMotherNatureCurrentActionPhase((byte)4);
        effect.enable(player);
        assertEquals(6, player.getMovementMotherNatureCurrentActionPhase());
        assertEquals(2, effect.getCoinsForEffect());
        assertEquals("EFFECT: you can move mother nature up to 2 more islands than indicated on the assistant card you played", effect.getDescription());
    }
}