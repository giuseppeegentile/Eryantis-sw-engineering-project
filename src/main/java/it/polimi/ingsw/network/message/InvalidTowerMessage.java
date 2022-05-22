package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

public class InvalidTowerMessage extends Message{
    private static final long serialVersionUID = 4245063092764274017L;
    private final ColorTower colorTower;

    /**
     * Message shown when a player chooses the wring tower's color
     * @param nickname current player
     * @param colorTower color chosen
     */

    public InvalidTowerMessage(String nickname, ColorTower colorTower) {
        super(nickname, MessageType.ERROR);
        this.colorTower = colorTower;
    }

    public ColorTower getColorTower(){
        return this.colorTower;
    }

    @Override
    public String toString() {
        return "InvalidTowerMessage{" +
                "player=" + getNickname() +
                '}';
    }
}
