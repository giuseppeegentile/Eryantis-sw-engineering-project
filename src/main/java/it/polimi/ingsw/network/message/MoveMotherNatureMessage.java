package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serial;

public class MoveMotherNatureMessage extends Message{

    private static final long serialVersionUID = 1566360713494368537L;
    IslandModel islandModel;

    PlayerModel playerModel;

    AssistantCardModel assistantCardModel;

    public MoveMotherNatureMessage(PlayerModel playerModel, IslandModel islandModel, AssistantCardModel assistantCardModel){
        super(playerModel.getNickname(), MessageType.MOVE);
        this.playerModel = playerModel;
        this.islandModel = islandModel;
        this.assistantCardModel = assistantCardModel;
    }
    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "nickname=" + playerModel.getNickname() +
                ", position=" + islandModel.getMotherNature() +
                ", assistant=" + assistantCardModel.getMotherNatureMovement() +
                '}';
    }
}


