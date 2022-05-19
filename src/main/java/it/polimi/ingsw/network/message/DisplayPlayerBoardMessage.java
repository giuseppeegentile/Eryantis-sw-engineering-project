package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

public class DisplayPlayerBoardMessage extends DisplayMessage {

    private static final long serialVersionUID = 1872426844665326365L;
    private final ObjectDisplay objectDisplay;
    private final Map<ColorPawns, Integer> hall;
    private final List<ColorTower> towers;
    private final List<ColorPawns> entrance;
    private final List<ColorPawns> profs;



    public DisplayPlayerBoardMessage(String nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs) {
        super(nickname);
        this.objectDisplay = ObjectDisplay.BOARD;
        this.towers  =towers;
        this.hall = hall;
        this.entrance = entrance;
        this.profs = profs;
    }


    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    @Override
    public String toString() {
        return "DisplayEntranceMessage{" +
                "player=" + getNickname() +
                ", hall=" + getHall() +
                ", entrance=" + getEntrance() +
                ", prof=" + getProfs() +
                ", objectDisplay=" + objectDisplay +
                '}';
    }

    public List<ColorPawns> getProfs() {
        return profs;
    }

    public List<ColorPawns> getEntrance() {
        return entrance;
    }

    public Map<ColorPawns, Integer> getHall() {
        return hall;
    }

    public List<ColorTower> getTowers() {
        return towers;
    }
}