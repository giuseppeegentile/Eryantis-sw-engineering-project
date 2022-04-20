package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameMode;

class GameModeResponse extends Message{

    private static final long serialVersionUID = -4419241297635925047L;
    private final GameMode gameMode;

    public GameModeResponse(String nickname, GameMode gameMode) {
        super(nickname, MessageType.PLAYERS_RESPONSE);
        this.gameMode = gameMode;

    }

    public GameMode getMode() {
        return this.gameMode;
    }

    @Override
    public String toString() {
        return "GameModeResponse{" +
                "nickname=" + getNickname() +
                ", gameMode=" + getMode() +
                '}';
    }
}