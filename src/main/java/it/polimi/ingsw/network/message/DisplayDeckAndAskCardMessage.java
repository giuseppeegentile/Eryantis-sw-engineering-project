package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

/**
 * Shows the card available (only the ones that can be played, so not equals to previous card played by other players), and the client wait for a response with the card that the player is going to play.
 */
public class DisplayDeckAndAskCardMessage extends DisplayMessage{

    private static final long serialVersionUID = 5207429579324913763L;
    private final List<AssistantCardModel> deck;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display deck and ask to play a card.
     * @param nickname current player.
     * @param deck player's deck.
     */
    public DisplayDeckAndAskCardMessage(String nickname, List<AssistantCardModel> deck) {
        super(nickname);
        this.objectDisplay = ObjectDisplay.DECK;
        this.deck = deck;
    }

    /**
     * @return the player's deck.
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
    /**
     *
     * @return The object to display to the view. In this case is the deck.
     */
    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}