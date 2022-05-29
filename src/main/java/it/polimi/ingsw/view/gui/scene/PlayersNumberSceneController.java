package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;

public class PlayersNumberSceneController extends ViewObservable implements GenericSceneController{

    private int minPlayers=0;
    private int maxPlayers=0;

    public void setPlayersRange(int minPlayers, int maxPlayers){
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }
}
