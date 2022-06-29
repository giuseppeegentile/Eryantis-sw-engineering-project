package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

/**
 * Message sent to display all the islands. Sent from server, received to client.
 */
public class DisplayIslandsMessage extends DisplayMessage{

    private static final long serialVersionUID = -3626482905376914288L;
    private final ObjectDisplay objectDisplay;
    private final List<IslandModel> islandModels;

    /**
<<<<<<< HEAD
     * Message sent to display all the islands
     * Parameters are set by the constructor
     * @param nickname player who's making the move
     * @param islandsModel model of the island
=======
     *
     * @param nickname player who's making the move.
     * @param islandsModel islands to be shown.
>>>>>>> main
     */
    public DisplayIslandsMessage(String nickname, List<IslandModel> islandsModel) {
        super(nickname);
        this.islandModels = islandsModel;
        this.objectDisplay = ObjectDisplay.ISLANDS;
    }

    /**
<<<<<<< HEAD
     * @return the island model
     */

=======
     * @return The islands to be shown.
     */
>>>>>>> main
    public List<IslandModel> getIslandModels() {
        return this.islandModels;
    }

    @Override
    public String toString() {
        return "DisplayIslandsMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + objectDisplay +
                ", islandModels=" + islandModels +
                '}';
    }

    /**
     *
     * @return The object to display to the view. In this case are the islands.
     */
    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
