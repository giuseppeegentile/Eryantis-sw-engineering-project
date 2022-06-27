package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

public class DisplayDeckMessage extends DisplayMessage{

    private static final long serialVersionUID = 5207429579324913763L;


    private final List<AssistantCardModel> deck;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display a player's deck
     * Parameters are set by the constructor
     * @param nickname player whom deck is shown
     * @param deck deck of the player
     */

    public DisplayDeckMessage(String nickname, List<AssistantCardModel> deck) {
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
        return "DisplayDeckMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", deck=" + deck +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
