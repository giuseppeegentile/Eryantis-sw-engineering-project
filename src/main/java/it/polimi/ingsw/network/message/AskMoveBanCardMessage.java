package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;
import java.util.List;

<<<<<<< HEAD

=======
/**
 * Sent from server to client after a client played the effect that allows to move ban card to island.
 */
>>>>>>> main
public class AskMoveBanCardMessage extends Message {

    private static final long serialVersionUID = 2849430098890890060L;
    private final List<IslandModel> islands;

    /**
<<<<<<< HEAD
     * Message sent to ask to ban a move after using the character card
     * Parameters are set by the constructor
     * @param active active player
     * @param islands list of islands
     */

=======
     * Message sent to ask to ban an island after using the character card.
     * @param active player that played the effect.
     * @param islands list of islands available.
     */
>>>>>>> main
    public AskMoveBanCardMessage(String active, List<IslandModel> islands) {
        super(active, MessageType.ASK_MOVE_BAN_CARD);
        this.islands = islands;
    }

    /**
<<<<<<< HEAD
     * @return the list of islands
     */

=======
     * @return the list of islands available.
     */
>>>>>>> main
    public List<IslandModel> getIslands() {
        return islands;
    }

    @Override
    public String toString() {
        return "askMoveBanCardMessage{" +
                "player=" + getNickname() +
                ", islands=" + islands +
                '}';
    }
}
