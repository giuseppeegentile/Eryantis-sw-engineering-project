package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class DisplayProfMessage extends DisplayMessage{
    private static final long serialVersionUID = 898861397842651060L;

    private final List<ColorPawns> profs;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display a player's profs
     * Parameters are set by the constructor
     * @param nickname current player
     * @param profs current player's profs
     */

    public DisplayProfMessage(String nickname, List<ColorPawns> profs) {
        super(nickname);
        this.profs = profs;
        this.objectDisplay = ObjectDisplay.ENTRANCE;
    }

    /**
     * @return the player's profs
     */

    public List<ColorPawns> getProfs() {
        return this.profs;
    }


    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        return "DisplayProfMessage{" +
                "player=" + getNickname() +
                ", profs=" + getProfs() +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}
