package it.polimi.ingsw.network.message;

public class EffectSkippingInfluenceMessage extends Message {
    private static final long serialVersionUID = -6680026523707068249L;

    /**
     * Message sent to when skipping the influence after using a character card
     * Parameters are set by the constructor
     * @param activeNick active player
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
