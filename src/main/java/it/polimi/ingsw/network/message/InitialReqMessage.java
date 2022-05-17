package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

public class InitialReqMessage extends Message{

    private static final long serialVersionUID = -2372566173559326843L;
    private final int numberPlayers;
    private final ColorTower colorTower;
    private final GameMode gameMode;

    public InitialReqMessage(String nickname, int numberPlayers, ColorTower colorTower, GameMode gameMode) {
        super(nickname, MessageType.INIT);
        this.colorTower = colorTower;
        this.numberPlayers = numberPlayers;
        this.gameMode = gameMode;
    }

    public int getNumberPlayers() {
        return numberPlayers;
    }

    public ColorTower getColorTower() {
        return colorTower;
    }
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
