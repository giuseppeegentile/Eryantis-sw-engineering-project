package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;
import java.util.Map;

public class AskStudentsChangeEntranceHall extends Message {

    private static final long serialVersionUID = 545786380414644164L;
    private final List<ColorPawns> entrance;
    private final Map<ColorPawns, Integer> hall;

    public AskStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall) {
        super(active, MessageType.ASK_CHANGE_ENTRANCE_HALL);
        this.entrance = entrance;
        this.hall = hall;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }

    public Map<ColorPawns, Integer> getHall() {
        return hall;
    }

    @Override
    public String toString() {
        return "AskStudentsChangeEntranceHall{" +
                "player=" + getNickname() +
                ", entrance=" + entrance +
                ", hall=" + hall +
                '}';
    }
}
