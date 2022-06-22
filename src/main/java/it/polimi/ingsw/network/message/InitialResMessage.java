package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

import java.util.List;

//from server to client
public class InitialResMessage extends Message{

    private static final long serialVersionUID = 4898299466144192135L;

    private final List<ColorTower> availableTowers;
    private GameMode gameMode;

    public InitialResMessage(String nickname, List<ColorTower> availableTowers, GameMode gameMode) {
        super(nickname, MessageType.INIT);
        this.availableTowers = availableTowers;
        this.gameMode = gameMode;
    }

    public List<ColorTower> getAvailableTowers() {
        return availableTowers;
    }

    public GameMode getGameMode(){
        return gameMode;
    }
    @Override
    public String toString() {
        return "InitialResMessage{" +
                "player=" + getNickname() +
                ", availableTowers=" + availableTowers +
                '}';
    }

}
