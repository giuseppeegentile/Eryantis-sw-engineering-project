package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import java.util.List;
import java.util.Map;

public class AskStudentsChangeEntranceHall extends Message {

    private static final long serialVersionUID = 545786380414644164L;
    private final List<ColorPawns> entrance;
    private final Map<ColorPawns, Integer> hall;

    /**
     * Message sent to ask to exchange students from hall to entrance
     * Parameters are set by the constructor
     * @param active active player
     * @param entrance player's entrance
     * @param hall player's hall
     */

    public AskStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall) {
        super(active, MessageType.ASK_CHANGE_ENTRANCE_HALL);
        this.entrance = entrance;
        this.hall = hall;
    }

    /**
     * @return the player's entrance
     */

    public List<ColorPawns> getEntrance() {
        return entrance;
    }

    /**
     * @return the player's hall
     */

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
