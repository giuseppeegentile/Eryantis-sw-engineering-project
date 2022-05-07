package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.Map;

public class DisplayHallMessage extends Message{
    private static final long serialVersionUID = -8556076477082526374L;

    private final Map<ColorPawns, Integer> hall;

    public DisplayHallMessage(String nickname, Map<ColorPawns, Integer> hall) {
        super(nickname, MessageType.DISPLAY);
        this.hall = hall;

    }

    public Map<ColorPawns, Integer> getHall() {
        return hall;
    }
}
