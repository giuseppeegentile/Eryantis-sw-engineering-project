package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

public class TowerMessage extends Message {

    private static final long serialVersionUID = 4556379693241389810L;

    private final ColorTower colorTower;
    private final int towerNumber;

    public TowerMessage(String nickname, ColorTower colorTower, int towerNumber) {
        super(nickname, MessageType.DISPLAY);

        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }

    public ColorTower getColorTower() {
        return colorTower;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    @Override
    public String toString() {
        return "TowerMessage{" +
                "player=" + getNickname() +
                "colorTower=" + colorTower +
                ", towerNumber=" + towerNumber +
                '}';
    }
}
