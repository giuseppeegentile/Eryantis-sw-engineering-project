package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class DisplayEntranceMessage extends DisplayMessage{
    private static final long serialVersionUID = 6447215261344959952L;
    private final  List<ColorPawns> entrance;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display a player's board's entrance
     * Parameters are set by the constructor
     * @param nickname player whom entrance is shown
     * @param entrance entrance shown
     */

    public DisplayEntranceMessage(String nickname, List<ColorPawns> entrance) {
        super(nickname);
        this.entrance = entrance;
        this.objectDisplay = ObjectDisplay.ENTRANCE;
    }

    /**
     * @return the player's entrance
     */

    public List<ColorPawns> getEntrance() {
        return entrance;
    }


    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        return "DisplayEntranceMessage{" +
                "player=" + getNickname() +
                ", entrance=" + entrance +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}
