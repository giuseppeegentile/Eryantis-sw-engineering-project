package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class DisplayEntranceMessage extends DisplayMessage{
    private static final long serialVersionUID = 6447215261344959952L;
    private final  List<ColorPawns> entrance;
    private final ObjectDisplay objectDisplay;

    public DisplayEntranceMessage(String nickname, List<ColorPawns> entrance) {
        super(nickname);
        this.entrance = entrance;
        this.objectDisplay = ObjectDisplay.ENTRANCE;
    }

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
