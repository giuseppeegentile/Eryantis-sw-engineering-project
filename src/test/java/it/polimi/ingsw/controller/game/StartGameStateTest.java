package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StartGameStateTest {

    @Test
    void initialConfigurationIslandSize() {
        GameModel gameModel = GameModel.getInstance();

        List<PlayerModel> playersModels = new ArrayList<>();
        PlayerModel player1 = new PlayerModel("davide");
        playersModels.add(player1);
        PlayerModel player2 = new PlayerModel("christian");
        playersModels.add(player2);
        PlayerModel player3 = new PlayerModel("giuseppe");
        playersModels.add(player3);
        PlayerModel player4 = new PlayerModel("quarto");
        playersModels.add(player4);

        List<ColorTower> colorTowers = new ArrayList<>(Arrays.asList(ColorTower.BLACK, ColorTower.WHITE, ColorTower.BLACK, ColorTower.WHITE));
        GameMode princ = GameMode.PRINCIPIANTE;

        StartGameState ssg = new StartGameState(gameModel);
        ssg.setInitialGameConfiguration(playersModels, colorTowers, princ);
        assertEquals(12, gameModel.getIslandsModel().size() );
    }


    //funziona ma non si può verificare.. genera dei valori casuali
    @Test
    void fillListWithColors(){
        GameModel gameModel = GameModel.getInstance();
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        StartGameState ssg= new StartGameState(gameModel);
        colors = ssg.fillListWithColors(colors, 10, 2);
        colors.forEach(c->{
            System.out.println(c.toString());
        });
    }
}