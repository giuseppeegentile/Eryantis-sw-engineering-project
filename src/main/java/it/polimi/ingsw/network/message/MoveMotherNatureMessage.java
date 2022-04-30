package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;


public class MoveMotherNatureMessage extends Message{

    private static final long serialVersionUID = 1566360713494368537L;

    PlayerModel playerModel;

    byte movement;

    public MoveMotherNatureMessage(PlayerModel playerModel, byte movement){
        super(playerModel.getNickname(), MessageType.MOVE);
        this.playerModel = playerModel;
        this.movement = movement;
    }
    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "nickname=" + playerModel.getNickname() +
                ", motherMovement=" + movement +
                '}';
    }
}


