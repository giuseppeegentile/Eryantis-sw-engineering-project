package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

public class PlayerNicknameMessage extends Message{
    private static final long serialVersionUID = -8598871481251948405L;

    private final int numPlayers;
    private final ColorTower colorTower;
    private final GameMode gameMode;

    /**
     * Message shown when a player is asked to choose a nickname
     * @param nickname nickname chosen
     * @param numPlayers number of players
     * @param colorTower color chosen
     * @param gameMode game mode chosen
     */

    public PlayerNicknameMessage(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode) {
        super(nickname, MessageType.PLAYERS_REQUEST);
        this.colorTower = colorTower;
        this.numPlayers = numPlayers;
        this.gameMode = gameMode;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public ColorTower getColorTower() {
        return this.colorTower;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    @Override
    public String toString() {
        return "NumberOfPlayerMessage{" +
                "player=" + getNickname() +
                ", colorTowerOfPlayer=" + this.getNumPlayers() +
                '}';
    }



}
