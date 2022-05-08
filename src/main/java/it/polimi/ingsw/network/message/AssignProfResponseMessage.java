package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

import java.util.List;

public class AssignProfResponseMessage extends  Message{
    private static final long serialVersionUID = -7897108701251560713L;

    private final List<ColorPawns> profs;

    public AssignProfResponseMessage(String nickname, List<ColorPawns> profs) {
        super(nickname, MessageType.DISPLAY);
        this.profs = profs;
    }

    @Override
    public String toString() {
        return "AssignProfResponseMessage{" +
                "player="+getNickname()+
                "profs=" + profs +
                '}';
    }
}
