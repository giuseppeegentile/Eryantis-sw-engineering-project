package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

public class DisplayPlayerBoardMessage extends DisplayMessage {

    private static final long serialVersionUID = 1872426844665326365L;
    private final ObjectDisplay objectDisplay;


    public DisplayPlayerBoardMessage(String nickname) {
        super(nickname);
        this.objectDisplay = ObjectDisplay.BOARD;
    }


    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        PlayerModel player = GameModel.getInstance().getPlayerByNickname(getNickname());
        return "DisplayEntranceMessage{" +
                "player=" + getNickname() +
                ", hall=" + player.getStudentInHall() +
                ", entrance=" + player.getStudentInEntrance() +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}