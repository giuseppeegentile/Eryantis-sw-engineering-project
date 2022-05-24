package it.polimi.ingsw.network.message;

public class EffectSkippingInfluenceMessage extends Message {
    private static final long serialVersionUID = -6680026523707068249L;

    public EffectSkippingInfluenceMessage(String activeNick) {
        super(activeNick, MessageType.SKIPPING_INFLUENCE);
    }

    @Override
    public String toString() {
        return "EffectSkippingInfluenceMessage{" +
                "player=" + getNickname() + "}";
    }
}
