package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enums.GameMode;

class GameModeResponse extends Message{

    private static final long serialVersionUID = -4419241297635925047L;
    private final GameMode gameMode;

    /**
     * Message sent as a response when selecting game mode
     * Parameters are set by the constructor
     *  @param nickname current player
     * @param gameMode game mode chosen
     */

    public GameModeResponse(String nickname, GameMode gameMode) {
        super(nickname, MessageType.PLAYERS_RESPONSE);
        this.gameMode = gameMode;

    }

    /**
     * @return the game mode
     */

    public GameMode getMode() {
        return this.gameMode;
    }

    @Override
    public String toString() {
        return "GameModeResponse{" +
                "player=" + getNickname() +
                ", gameMode=" + getMode() +
                '}';
    }
}