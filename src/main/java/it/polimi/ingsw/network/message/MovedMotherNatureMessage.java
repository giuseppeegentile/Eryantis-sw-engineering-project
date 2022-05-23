package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;


public class MovedMotherNatureMessage extends Message{

    private static final long serialVersionUID = 1566360713494368537L;

    private final PlayerModel playerModel;

    private final byte movement;

    public MovedMotherNatureMessage(String player, byte movement){
        super(player, MessageType.PLAYER_MOVED_MOTHER);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(player);
        this.movement = movement;
    }

    public byte getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "player=" + playerModel.getNickname() +
                ", motherMovement=" + movement +
                '}';
    }
}

