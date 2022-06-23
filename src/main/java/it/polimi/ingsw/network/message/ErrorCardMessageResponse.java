package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

public class ErrorCardMessageResponse extends Message{
    private static final long serialVersionUID = 5424490518304822502L;
    private final AssistantCardModel card;

    /**
     * Message sent as a response of an error when playing a card
     * Parameters are set by the constructor
     * @param nickname current player
     * @param card card played
     */

    public ErrorCardMessageResponse(String nickname, AssistantCardModel card) {
        super(nickname, MessageType.ERROR);
        this.card = card;
    }

    /**
     * @return the card selected
     */

    public AssistantCardModel getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "ErrorCardMessageResponse{" +
                "player=" + getNickname() +
                ", card=" + card +
                '}';
    }
}
