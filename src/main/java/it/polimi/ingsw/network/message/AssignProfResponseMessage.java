package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class AssignProfResponseMessage extends  Message{
    private static final long serialVersionUID = -7897108701251560713L;

    private final List<ColorPawns> profs;

    /**
     * Message sent as a response to the assignment of a prof to a player
     * Parameters are set by the constructor
     * @param nickname nickname of the player to which a prof is assigned
     * @param profs prof assigned to a player
     */

    public AssignProfResponseMessage(String nickname, List<ColorPawns> profs) {
        super(nickname, MessageType.DISPLAY);
        this.profs = profs;
    }

    @Override
    public String toString() {
        return "AssignProfResponseMessage{" +
                "player="+getNickname()+
                ", profs=" + profs +
                '}';
    }
}
