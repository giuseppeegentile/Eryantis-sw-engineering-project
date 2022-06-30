package it.polimi.ingsw.network.message;

/**
 * Sent to client to request the color to remove for all player. Sent after the play of the card.
 */
public class AskColorRemoveFromAll extends Message {

    private static final long serialVersionUID = 5650419507381102124L;

    /**
     * Message sent to ask which color remove for every player, after the correspondent effect card has been played.
     * @param active player that played the effect
     */
    public AskColorRemoveFromAll(String active) {
        super(active, MessageType.COLOR_REMOVE_ALL);
    }

    @Override
    public String toString() {
        return "AskColorRemoveFromAll{" +
                "player=" + getNickname() +
                '}';
    }
}
