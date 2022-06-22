package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class DisplayProfMessage extends DisplayMessage{
    private static final long serialVersionUID = 898861397842651060L;

    private final List<ColorPawns> profs;
    private final ObjectDisplay objectDisplay;

    /**
     * Message shown to display a player's profs
     * @param nickname current player
     * @param profs current player's profs
     */

    public DisplayProfMessage(String nickname, List<ColorPawns> profs) {
        super(nickname);
        this.profs = profs;
        this.objectDisplay = ObjectDisplay.ENTRANCE;
    }

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
