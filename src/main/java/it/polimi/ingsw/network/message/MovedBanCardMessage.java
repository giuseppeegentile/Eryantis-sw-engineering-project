package it.polimi.ingsw.network.message;

<<<<<<< HEAD
=======
/**
 *  Client to server. Message sent when playing a character card with a prohibition effect
 */

>>>>>>> main
public class MovedBanCardMessage extends Message {

    private static final long serialVersionUID = -7727095324726678974L;
    private final int indexIsland;

    /**
<<<<<<< HEAD
     * Message sent when playing a character card with a prohibition effect
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param active current player
     * @param indexIsland index of the island chosen by the player to be banned
     */

    public MovedBanCardMessage(String active, int indexIsland) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    /**
     * @return index of the island chosen
     */

    public int getIndexIsland() {
        return indexIsland;
    }

    @Override
    public String toString() {
        return "MovedBanCardMessage{" +
                "player=" + getNickname() +
                ", indexIsland=" + indexIsland +
                '}';
    }
}
