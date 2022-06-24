package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

public class TowerMessage extends Message {

    private static final long serialVersionUID = 4556379693241389810L;

    private final ColorTower colorTower;
    private final int towerNumber;

    /**
     * Message sent to display the player's towers
     * Parameters are set by the constructor
     * @param nickname current player
     * @param colorTower towers' color
     * @param towerNumber number of towers
     */

    public TowerMessage(String nickname, ColorTower colorTower, int towerNumber) {
        super(nickname, MessageType.DISPLAY);

        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }

    /**
     * @return the towers' color
     */

    public ColorTower getColorTower() {
        return colorTower;
    }

    /**
     * @return the towers' number
     */

    public int getTowerNumber() {
        return towerNumber;
    }

    @Override
    public String toString() {
        return "TowerMessage{" +
                "player=" + getNickname() +
                ", colorTower=" + colorTower +
                ", towerNumber=" + towerNumber +
                '}';
    }
}
