package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ControlProfEffectTest {

    @Test
    void controlProfEffect(){
        ControlProfEffect effect = new ControlProfEffect();
        PlayerModel player = new PlayerModel("Gandalf");
        PlayerModel player1 = new PlayerModel("Non mi ricordo il nome");
        player.addProf(ColorPawns.PINK);
        player.addProf(ColorPawns.RED);
        player1.addProf(ColorPawns.GREEN);
        List<PlayerModel> players = new ArrayList<>();
        players.add(player);
        players.add(player1);
        GameModel.getInstance().setPlayers(players);
        Map<ColorPawns, PlayerModel> oldAssociationProfs = new HashMap<>();
        oldAssociationProfs.put(ColorPawns.PINK, player);
        oldAssociationProfs.put(ColorPawns.RED, player);
        oldAssociationProfs.put(ColorPawns.GREEN, player1);
        effect.enable(player);
        assertEquals(oldAssociationProfs, effect.getOldAssociationProfs());
        assertTrue(GameModel.getInstance().getPlayerByNickname("Gandalf").getProfs().containsAll(asList(ColorPawns.GREEN, ColorPawns.BLUE, ColorPawns.RED, ColorPawns.PINK, ColorPawns.YELLOW)));
        assertEquals(3, effect.getCoinsForEffect());
        assertNull(effect.getDescription());
        assertEquals(player, effect.getPlayerWithProfs());
    }

}