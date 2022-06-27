package it.polimi.ingsw.network.message;

public class ExtraGetInfluence extends Message {

    private static final long serialVersionUID = 7916652247877017654L;
    private final int indexIsland;

    /**
     * Message sent when getting extra influence after playing the character card
     * Parameters are set by the constructor
     * @param active active player
     * @param indexIsland index of the island chosen by the player
     */

    public ExtraGetInfluence(String active, int indexIsland) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    /**
     * @return the index of the island chosen by the player
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
