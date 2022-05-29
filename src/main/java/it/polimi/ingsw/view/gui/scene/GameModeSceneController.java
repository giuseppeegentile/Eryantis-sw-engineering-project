package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.observer.ViewObservable;

import java.util.List;

public class GameModeSceneController extends ViewObservable implements GenericSceneController{

    GameMode gameMode;

    public void setGameMode() {
        this.gameMode = gameMode;
    }
}
