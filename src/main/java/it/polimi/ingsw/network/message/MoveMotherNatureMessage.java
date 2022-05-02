package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;


public class MoveMotherNatureMessage extends Message{

    private static final long serialVersionUID = 1566360713494368537L;

    private final PlayerModel playerModel;

    private final byte movement;

    public MoveMotherNatureMessage(String playerModel, byte movement){
        super(playerModel, MessageType.MOVE);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(playerModel);
        this.movement = movement;
    }

    public byte getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "nickname=" + playerModel.getNickname() +
                ", motherMovement=" + movement +
                '}';
    }
}


