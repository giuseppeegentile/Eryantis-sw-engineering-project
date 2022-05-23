package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class StudentToIslandMessage extends Message{
    private static final long serialVersionUID = 1729951793698626264L;

    private final List<ColorPawns> entrance;

    public StudentToIslandMessage(String nickname, List<ColorPawns> entrance) {
        super(nickname, MessageType.REQ_ENTRANCE_TO_HALL);
        this.entrance = entrance;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }


    @Override
    public String toString() {
        return "StudentToIslandMessage{" +
                "player=" + getNickname() +
                ", students=" + entrance +
                '}';
    }

}
