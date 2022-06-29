package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

/**
 * Client to server. Message sent when a player is asked to choose a nickname
 */

public class PlayerNicknameMessage extends Message{
    private static final long serialVersionUID = -8598871481251948405L;

    private final int numPlayers;
    private final ColorTower colorTower;
    private final GameMode gameMode;

    /**
<<<<<<< HEAD
     * Message sent when a player is asked to choose a nickname
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
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

    /**
     * @return the number of players
     */

    public int getNumPlayers() {
        return this.numPlayers;
    }

    /**
     * @return the color of the towers
     */

    public ColorTower getColorTower() {
        return this.colorTower;
    }

    /**
     * @return the game mode chosen
     */

    public GameMode getGameMode() {
        return this.gameMode;
    }

    @Override
    public String toString() {
        return "PlayerNicknameMessage{" +
                "player=" + getNickname() +
                ", colorTowerOfPlayer=" + this.getNumPlayers() +
                '}';
    }



}
