package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class DisplayEntranceMessage extends Message{
    private static final long serialVersionUID = 6447215261344959952L;
    private final  List<ColorPawns> entrance;

    public DisplayEntranceMessage(String nickname, List<ColorPawns> entrance) {
        super(nickname, MessageType.DISPLAY);
        this.entrance = entrance;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }
}
