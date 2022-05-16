package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

public class DisplayPlayerBoardMessage extends DisplayMessage {

    private static final long serialVersionUID = 1872426844665326365L;
    private final PlayerModel player;
    private final ObjectDisplay objectDisplay;


    public DisplayPlayerBoardMessage(String nickname, PlayerModel player) {
        super(nickname);
        this.player = player;
        this.objectDisplay = ObjectDisplay.BOARD;
    }

    public PlayerModel getPlayer(){
        return this.player;
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        return "DisplayEntranceMessage{" +
                "player=" + getNickname() +
                "hall=" + player.getStudentInHall() +
                "entrance=" + player.getStudentInEntrance() +
                ", objectDisplay=" + objectDisplay +
                '}';
    }
}