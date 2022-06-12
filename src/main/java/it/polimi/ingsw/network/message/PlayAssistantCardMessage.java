package it.polimi.ingsw.network.message;

public class PlayAssistantCardMessage extends Message{

    private static final long serialVersionUID = -7852587909172148582L;
    private final String playerModel;
    private final int indexAssistantCardModel;

    public PlayAssistantCardMessage(String playerModel, int indexAssistantCardModel){
        super(playerModel, MessageType.PLAYED_ASSISTANT_CARD);
        this.playerModel = playerModel;
        this.indexAssistantCardModel = indexAssistantCardModel;
    }

    public int getIndexCard(){
        return indexAssistantCardModel;
    }

    @Override
    public String toString() {
        return "PlayAssistantCardMessage{" +
                "player=" + playerModel +
                ", indexAssistantCardModel=" + indexAssistantCardModel +
                '}';
    }
}
