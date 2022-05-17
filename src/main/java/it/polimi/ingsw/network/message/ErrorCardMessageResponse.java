package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

public class ErrorCardMessageResponse extends Message{
    private static final long serialVersionUID = 5424490518304822502L;
    private final AssistantCardModel card;

    public ErrorCardMessageResponse(String nickname, AssistantCardModel card) {
        super(nickname, MessageType.ERROR);
        this.card = card;
    }

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
