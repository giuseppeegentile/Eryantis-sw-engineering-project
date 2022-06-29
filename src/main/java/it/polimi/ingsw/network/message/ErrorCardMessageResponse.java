package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;

/**
 * Message sent as a response of an error when playing a card. The server checks if the card can be played.
 * In case it can't be played, the server sends this message.
 */
public class ErrorCardMessageResponse extends Message{
    private static final long serialVersionUID = 5424490518304822502L;
    private final AssistantCardModel card;

    /**
<<<<<<< HEAD
     * Message sent as a response of an error when playing a card
     * Parameters are set by the constructor
     * @param nickname current player
     * @param card card played
=======
     * Parameters are set by the constructor.
     * @param nickname current player.
     * @param card card played.
>>>>>>> main
     */

    public ErrorCardMessageResponse(String nickname, AssistantCardModel card) {
        super(nickname, MessageType.ERROR);
        this.card = card;
    }

    /**
<<<<<<< HEAD
     * @return the card selected
     */

=======
     * @return the card that can't be played.
     */
>>>>>>> main
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
