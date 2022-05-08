package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.Map;

public class DisplayHallMessage extends Message{
    private static final long serialVersionUID = -8556076477082526374L;

    private final Map<ColorPawns, Integer> hall;
    private final ObjectDisplay objectDisplay;

    public DisplayHallMessage(String nickname,Map<ColorPawns, Integer> hall) {
        super(nickname, MessageType.DISPLAY);
        this.hall = hall;
        this.objectDisplay = ObjectDisplay.HALL;
    }

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
                "hall=" + hall +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}
