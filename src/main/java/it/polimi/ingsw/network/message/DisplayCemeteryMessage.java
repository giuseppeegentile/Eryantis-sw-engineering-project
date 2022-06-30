package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

/**
 * Message sent to display cards played, used at the start of the global round for each player before he plays the card. Sent from server, received to client.
 */
public class DisplayCemeteryMessage extends DisplayMessage{
    private static final long serialVersionUID = -7509713511298375285L;

    private final List<AssistantCardModel> cemetery;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display game's cemetery.
     * @param nickname the nickname of the player whom cemetery is displayed
     * @param cemetery the cemetery, a list of cards played.
     */

    public DisplayCemeteryMessage(String nickname, List<AssistantCardModel> cemetery) {
        super(nickname);
        this.cemetery = cemetery;
        this.objectDisplay = ObjectDisplay.CEMETERY;
    }

    /**
     * @return the cemetery
     */

    public List<AssistantCardModel> getCemetery() {
        return cemetery;
    }

    @Override
    public String toString() {
        return "DisplayCemeteryMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", cemetery=" + cemetery +
                '}';
    }
    /**
     *
     * @return The object to display to the view. In this case are the cards played.
     */
    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
