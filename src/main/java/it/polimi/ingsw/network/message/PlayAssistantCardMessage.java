package it.polimi.ingsw.network.message;

/**
 * Client to server. Message sent when playing an assistant card
 */

public class PlayAssistantCardMessage extends Message{

    private static final long serialVersionUID = -7852587909172148582L;
    private final String playerModel;
    private final int indexAssistantCardModel;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param playerModel current player
     * @param indexAssistantCardModel assistant card played
     */

    public PlayAssistantCardMessage(String playerModel, int indexAssistantCardModel){
        super(playerModel, MessageType.PLAYED_ASSISTANT_CARD);
        this.playerModel = playerModel;
        this.indexAssistantCardModel = indexAssistantCardModel;
    }

    /**
     * @return the assistant card played
     */

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
