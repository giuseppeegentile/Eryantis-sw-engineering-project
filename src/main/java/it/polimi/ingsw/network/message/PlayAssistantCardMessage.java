package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serial;

public class PlayAssistantCardMessage extends Message{
    
    private static final long serialVersionUID = -7852587909172148582L;
    PlayerModel playerModel;

    AssistantCardModel assistantCardModel;

    public PlayAssistantCardMessage(PlayerModel playerModel, AssistantCardModel assistantCardModel){
        super(playerModel.getNickname(), MessageType.PLAYERS_REQUEST);
        this.playerModel = playerModel;
        this.assistantCardModel = assistantCardModel;
    }

    @Override
    public String toString() {
        return "PlayAssistantCardMessage{" +
                "nickname=" + playerModel.getNickname() +
                ", priority=" + assistantCardModel.getPriority() +
                ", mother nature movement=" + assistantCardModel.getMotherNatureMovement() +
                '}';
    }
}
