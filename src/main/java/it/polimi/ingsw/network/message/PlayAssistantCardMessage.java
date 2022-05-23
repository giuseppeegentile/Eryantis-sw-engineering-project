package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;


public class PlayAssistantCardMessage extends Message{
    
    private static final long serialVersionUID = -7852587909172148582L;
    private final String playerModel;
    AssistantCardModel assistantCardModel;

    public PlayAssistantCardMessage(String playerModel, AssistantCardModel assistantCardModel){
        super(playerModel, MessageType.PLAYED_ASSISTANT_CARD);
        this.playerModel = playerModel;
        this.assistantCardModel = assistantCardModel;
    }

    public AssistantCardModel getCard(){
        return assistantCardModel;
    }

    @Override
    public String toString() {
        return "PlayAssistantCardMessage{" +
                "player=" + playerModel +
                ", priority=" + assistantCardModel.getPriority() +
                ", movement=" + assistantCardModel.getMotherNatureMovement() +
                '}';
    }
}
