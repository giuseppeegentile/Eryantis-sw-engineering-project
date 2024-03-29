package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;

import java.util.List;

/**
 * Message from the client to the server: notify the server with the choice of the player.
 */
public class ChosenTowerMessage extends Message{
    private static final long serialVersionUID = 759203857591040072L;
    private final ColorTower colorTower;

    /**
     * Message sent as a response to the choice of the tower's color by a player.
     * @param nickname nickname of the player who chooses the tower's color
     * @param colorTower color of the tower chosen by the player
     */

    public ChosenTowerMessage(String nickname, ColorTower colorTower) {
        super(nickname, MessageType.CHOSEN_TOWER);//MESSAGE TYPE
        this.colorTower = colorTower;
    }

    /**
     * @return color of the tower chosen.
     */
    public ColorTower getColorTowers() {
        return colorTower;
    }

    @Override
    public String toString() {
        return "ChosenTowerMessage{" +
                "player=" + getNickname() +
                ", colorTowers=" + colorTower +
                '}';
    }
}
