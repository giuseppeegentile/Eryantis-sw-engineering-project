package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandModelTest {

    @Test
    void getInfluence() {
        GameModel testGame = GameModel.getInstance();
        List<ColorPawns> studentOnIsland = new ArrayList<>(Arrays.asList(ColorPawns.BLUE,ColorPawns.BLUE,ColorPawns.BLUE,ColorPawns.BLUE));
        IslandModel islandModel = new IslandModel(false, studentOnIsland);

        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide", ColorTower.GREY);
        player1.setTowers(ColorTower.GREY, 5);
        player1.addProf(ColorPawns.GREEN);
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian", ColorTower.BLACK);
        player2.setTowers(ColorTower.BLACK, 5);
        player2.addProf(ColorPawns.BLUE);
        player2.addProf(ColorPawns.PINK);
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe", ColorTower.WHITE);
        player3.setTowers(ColorTower.GREY, 5);
        player3.addProf(ColorPawns.RED);
        playersModels.add(player3);
        testGame.setPlayers(playersModels);


        assertEquals(player2.getNickname(), islandModel.getInfluence(testGame).getNickname());
    }
}