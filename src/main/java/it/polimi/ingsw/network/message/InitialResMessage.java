package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

import java.util.List;

//from server to client
public class InitialResMessage extends Message{

    private static final long serialVersionUID = 4898299466144192135L;

    private final List<ColorTower> availableTowers;

    public InitialResMessage(String nickname, List<ColorTower> availableTowers) {
        super(nickname, MessageType.INIT);
        this.availableTowers = availableTowers;
    }

    public List<ColorTower> getAvailableTowers() {
        return availableTowers;
    }

    @Override
    public String toString() {
        return "InitialResMessage{" +
                "player=" + getNickname() +
                ", availableTowers=" + availableTowers +
                '}';
    }
}
