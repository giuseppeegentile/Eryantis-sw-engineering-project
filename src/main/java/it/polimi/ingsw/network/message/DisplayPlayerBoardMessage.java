package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
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
    private final PlayerModel player;

    /**
     * Message sent to display  player's game board
     * Parameters are set by the constructor
     * @param nickname player's whom game board is shown
     * @param towers towers of the current player
     * @param hall hall of the current player
     * @param entrance entrance of the current player
     * @param profs profs of the current player
     */

    public DisplayPlayerBoardMessage(PlayerModel nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs) {
        super(nickname.getNickname());
        this.player = nickname;
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
        return "DisplayPlayerBoardMessage{" +
                "player=" + getNickname() +
                ", hall=" + getHall() +
                ", entrance=" + getEntrance() +
                ", prof=" + getProfs() +
                ", objectDisplay=" + objectDisplay +
                '}';
    }

    /**
     * @return the player's profs
     */

    public List<ColorPawns> getProfs() {
        return profs;
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

    /**
     * @return the player's towers
     */

    public List<ColorTower> getTowers() {
        return towers;
    }

    /**
     * @return the player
     */
    public PlayerModel getPlayer() {
        return player;
    }
}