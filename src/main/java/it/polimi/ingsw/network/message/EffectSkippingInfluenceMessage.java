package it.polimi.ingsw.network.message;

<<<<<<< HEAD
=======
/**
 * Message sent from server to client to display the effect that skip the influence for this round.
 */
>>>>>>> main
public class EffectSkippingInfluenceMessage extends Message {
    private static final long serialVersionUID = -6680026523707068249L;

    /**
<<<<<<< HEAD
     * Message sent to when skipping the influence after using a character card
     * Parameters are set by the constructor
     * @param activeNick active player
     */

=======
     * Message sent to when skipping the influence after using a character card.
     * Uses the super constructor of Message.
     * @param activeNick Player that used the effect.
     */
>>>>>>> main
    public EffectSkippingInfluenceMessage(String activeNick) {
        super(activeNick, MessageType.SKIPPING_INFLUENCE);
    }

    @Override
    public String toString() {
        return "EffectSkippingInfluenceMessage{" +
                "player=" + getNickname() + "}";
    }
}
