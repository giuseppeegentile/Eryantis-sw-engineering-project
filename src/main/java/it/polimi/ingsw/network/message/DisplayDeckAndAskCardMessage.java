package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

public class DisplayDeckAndAskCardMessage extends DisplayMessage{

    private static final long serialVersionUID = 5207429579324913763L;
    private final List<AssistantCardModel> deck;
    private final ObjectDisplay objectDisplay;

    public DisplayDeckAndAskCardMessage(String nickname, List<AssistantCardModel> deck) {
        super(nickname);
        this.objectDisplay = ObjectDisplay.DECK;
        this.deck = deck;
    }

    public List<AssistantCardModel> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return "ShowCemeteryMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", deck=" + deck +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}