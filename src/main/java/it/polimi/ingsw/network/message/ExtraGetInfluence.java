package it.polimi.ingsw.network.message;

<<<<<<< HEAD
=======

/**
 * Message sent from client to server after the character card with extra influence has been played.
 */
>>>>>>> main
public class ExtraGetInfluence extends Message {

    private static final long serialVersionUID = 7916652247877017654L;
    private final int indexIsland;

    /**
<<<<<<< HEAD
     * Message sent when getting extra influence after playing the character card
     * Parameters are set by the constructor
     * @param active active player
     * @param indexIsland index of the island chosen by the player
     */

=======
     * @param active player that played the card with this effect.
     * @param indexIsland index of the island chosen by the player.
     */
>>>>>>> main
    public ExtraGetInfluence(String active, int indexIsland) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    /**
<<<<<<< HEAD
     * @return the index of the island chosen by the player
     */

=======
     * @return the index of the island chosen by the player.
     */
>>>>>>> main
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
