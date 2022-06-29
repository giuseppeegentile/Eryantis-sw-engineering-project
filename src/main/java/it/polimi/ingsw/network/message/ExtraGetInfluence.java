package it.polimi.ingsw.network.message;


/**
 * Message sent from client to server after the character card with extra influence has been played.
 */
public class ExtraGetInfluence extends Message {

    private static final long serialVersionUID = 7916652247877017654L;
    private final int indexIsland;

    /**
     * @param active player that played the card with this effect.
     * @param indexIsland index of the island chosen by the player.
     */
    public ExtraGetInfluence(String active, int indexIsland) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    /**
     * @return the index of the island chosen by the player.
     */
    public int getIndexIsland() {
        return indexIsland;
    }

    @Override
    public String toString() {
        return "ExtraGetInfluence{" +
                "player=" + getNickname() +
                ", indexIsland=" + indexIsland +
                '}';
    }
}
