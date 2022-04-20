package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.game.PhaseGame;
import it.polimi.ingsw.network.message.Message;

public class ContextGame {

    private GameState state;
    private PhaseGame phase;

    public ContextGame(){
        this.state = null;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
/*
    public void onMessageReceived(Message receivedMessage){
        switch (phase){
            case START:
                state = new StartGameState(GameModel.getInstance());
                break;
            case CHECK_WIN:
                state = new CheckWinState(GameModel.getInstance());
                break;
            case CHECK_ISLAND:
                //state = new CheckIslandState(GameModel.getInstance(), receivedMessage.getIsland());
                break;
            case CHECK_JOIN:
                //state = new CheckIfJoinableState(GameModel.getInstance(), receivedMessage.getIsland());
                break;
        }
    }*/
}
