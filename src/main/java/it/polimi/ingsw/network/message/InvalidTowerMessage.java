package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

/**
 *  Server to client. Message sent when a player chooses the wring tower's color
 */

public class InvalidTowerMessage extends Message{
    private static final long serialVersionUID = 4245063092764274017L;
    private final ColorTower colorTower;

    /**
<<<<<<< HEAD
     * Message sent when a player chooses the wring tower's color
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param nickname current player
     * @param colorTower color chosen
     */

    public InvalidTowerMessage(String nickname, ColorTower colorTower) {
        super(nickname, MessageType.ERROR);
        this.colorTower = colorTower;
    }

    /**
     * @return the towers' color
     */

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
