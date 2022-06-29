package it.polimi.ingsw.network.message;

/**
 * Generic message to display a particular object in the view of the client.
 * The object is described by the object display enum type.
 */
public class DisplayMessage extends Message{

    private static final long serialVersionUID = -732631174905728965L;
    private ObjectDisplay objectDisplay;

    /**
     * Shows a message to a player
     * @param nickname player that owns the object-to-display.
     */
    public DisplayMessage(String nickname) {
        super(nickname, MessageType.DISPLAY);
    }

    public ObjectDisplay getObjectDisplay() {
        return this.objectDisplay;
    }
}
