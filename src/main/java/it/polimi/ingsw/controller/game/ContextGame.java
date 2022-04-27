package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.enums.PhaseGame;

public class ContextGame {

    private GameState state;
    private PhaseGame phase;

    /**
     * Constructor for ContextGame: initializes state to the null value
     */
    public ContextGame(){
        this.state = null;
    }
    /**
     *
     * @return The current game state
     */
    public GameState getState() {
        return state;
    }

    /**
     *
     * @param state The next game state
     */
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
