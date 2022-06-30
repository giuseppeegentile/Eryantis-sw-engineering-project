package it.polimi.ingsw.network.message;

/**
 * Message sent from server to client to display the effect that skip the influence for this round.
 */
public class EffectSkippingInfluenceMessage extends Message {
    private static final long serialVersionUID = -6680026523707068249L;

    /**
     * Message sent to when skipping the influence after using a character card.
     * Uses the super constructor of Message.
     * @param activeNick Player that used the effect.
     */
    public EffectSkippingInfluenceMessage(String activeNick) {
        super(activeNick, MessageType.SKIPPING_INFLUENCE);
    }

    @Override
    public String toString() {
        return "EffectSkippingInfluenceMessage{" +
                "player=" + getNickname() + "}";
    }
}
