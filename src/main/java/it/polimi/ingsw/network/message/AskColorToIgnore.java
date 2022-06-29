package it.polimi.ingsw.network.message;

/**
 * Message sent to ask what color to ignore during influence count. Sent to client after the latter played a card with this effect.
 */
public class AskColorToIgnore extends Message {

    private static final long serialVersionUID = -7419976465222022178L;

    /**
     * Constructor for the message.
     * Message sent to ask to request the client to choose the color to ignore after activating the character card.
     * @param active player that played the effect.
     */
    public AskColorToIgnore(String active) {
        super(active, MessageType.ASK_COLOR_TO_IGNORE);
    }

    @Override
    public String toString() {
        return "AskColorToIgnore{" +
                "player=" + getNickname() +
                '}';
    }
}
