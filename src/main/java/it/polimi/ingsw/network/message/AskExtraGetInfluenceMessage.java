package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;
import java.util.List;

<<<<<<< HEAD

=======
/**
 * Sent from server to client after a client played the effect that allows to calculate extra influence the island mother nature will end.
 */
>>>>>>> main
public class AskExtraGetInfluenceMessage extends Message {

    private static final long serialVersionUID = 8524765326714118718L;
    private final List<IslandModel> islands;

    /**
<<<<<<< HEAD
     * Message sent to ask to get extra influence after activating the character card
     * Parameters are set by the constructor
     * @param active active player
     * @param islands list of islands
     */

=======
     * Message sent to ask to calculate extra influence after activating the character card.
     * @param active player that played the effect.
     * @param islands list of islands available.
     */
>>>>>>> main
    public AskExtraGetInfluenceMessage(String active, List<IslandModel> islands) {
        super(active, MessageType.ASK_EXTRA_GET_INFLUENCE);
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
        return "askExtraGetInfluenceMessage{" +
                "player=" + getNickname() +
                ", islands=" + islands +
                '}';
    }
}
