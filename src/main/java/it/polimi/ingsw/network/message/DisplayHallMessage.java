package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.Map;

public class DisplayHallMessage extends DisplayMessage{
    private static final long serialVersionUID = -8556076477082526374L;

    private final Map<ColorPawns, Integer> hall;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display a player's game board's hall
     * Parameters are set by the constructor
     * @param nickname player whom hall is shown
     * @param hall hall shown
     */
    public DisplayHallMessage(String nickname, Map<ColorPawns, Integer> hall) {
        super(nickname);
        this.hall = hall;
        this.objectDisplay = ObjectDisplay.HALL;
    }

    /**
     * @return the player's hall
     */

    public Map<ColorPawns, Integer> getHall() {
        return hall;
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        return "DisplayHallMessage{" +
                "player=" + getNickname() +
                ", hall=" + hall +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}
