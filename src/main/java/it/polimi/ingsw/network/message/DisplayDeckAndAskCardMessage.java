package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

public class DisplayDeckAndAskCardMessage extends DisplayMessage{

    private static final long serialVersionUID = 5207429579324913763L;
    private final List<AssistantCardModel> deck;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display deck and ask to play a card
     * Parameters are set by the constructor
     * @param nickname current player
     * @param deck player's deck
     */

    public DisplayDeckAndAskCardMessage(String nickname, List<AssistantCardModel> deck) {
        super(nickname);
        this.objectDisplay = ObjectDisplay.DECK;
        this.deck = deck;
    }

    /**
     * @return the player's deck
     */

    public List<AssistantCardModel> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return "DisplayDeckAndAskCardMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", deck=" + deck +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}