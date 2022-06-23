package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

public class InitialReqMessage extends Message{

    private static final long serialVersionUID = -2372566173559326843L;
    private final int numberPlayers;
    private final ColorTower colorTower;
    private final GameMode gameMode;

    /**
     * Message sent to ask the initial game configuration
     * Parameters are set by the constructor
     * @param nickname current player
     * @param numberPlayers number of players
     * @param colorTower current player tower color
     * @param gameMode game mode chosen
     */

    public InitialReqMessage(String nickname, int numberPlayers, ColorTower colorTower, GameMode gameMode) {
        super(nickname, MessageType.INIT);
        this.colorTower = colorTower;
        this.numberPlayers = numberPlayers;
        this.gameMode = gameMode;
    }

    /**
     * @return the number of players
     */

    public int getNumberPlayers() {
        return numberPlayers;
    }

    /**
     * @return the color of the towers
     */

    public ColorTower getColorTower() {
        return colorTower;
    }

    /**
     * @return the game mode chosen
     */
    public GameMode getGameMode() {
        return this.gameMode;
    }
    @Override
    public String toString() {
        return "InitialReqMessage{" +
                "player=" + getNickname() +
                ", numberPlayers=" + numberPlayers +
                ", colorTower=" + colorTower +
                '}';
    }
}
