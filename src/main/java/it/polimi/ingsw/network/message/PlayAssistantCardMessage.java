package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;


public class PlayAssistantCardMessage extends Message{
    
    private static final long serialVersionUID = -7852587909172148582L;
    PlayerModel playerModel;

    AssistantCardModel assistantCardModel;

    public PlayAssistantCardMessage(String playerModel, AssistantCardModel assistantCardModel){
        super(playerModel, MessageType.PLAYERS_REQUEST);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(playerModel);
        this.assistantCardModel = assistantCardModel;
    }

    public AssistantCardModel getCard(){
        return assistantCardModel;
    }

    @Override
    public String toString() {
        return "PlayAssistantCardMessage{" +
                "player=" + playerModel.getNickname() +
                ", priority=" + assistantCardModel.getPriority() +
                ", movement=" + assistantCardModel.getMotherNatureMovement() +
                '}';
    }
}
