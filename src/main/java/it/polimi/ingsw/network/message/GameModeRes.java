package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enums.GameMode;

public class GameModeRes extends Message{

    private static final long serialVersionUID = 1622383409250771615L;

    /**
     * Message sent as a response to the game mode's request
     * Parameters are set by the constructor
     */

    private final GameMode gameMode;

    public GameModeRes(String nickname, GameMode gameMode) {
        super(nickname, MessageType.GAMEMODE_RES);
        this.gameMode = gameMode;
    }

    /**
     * @return the game mode chosen
     */

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public String toString() {
        return "GameModeRes{" +
                "player=" + getNickname() +
                ", gameMode=" + gameMode +
                '}';
    }
}
