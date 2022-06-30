package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enums.GameMode;

/**
 * Message sent as a response to the game mode's server request. Sent from client to server with game mode chosen.
 */
public class GameModeRes extends Message{

    private static final long serialVersionUID = 1622383409250771615L;


    private final GameMode gameMode;

    /**
     * Constructor, set the params of the class
     * @param nickname nickname that have chosen the game mode
     * @param gameMode game mode, the game controller is going to elaborate the game based on this.
     */
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
