package it.polimi.ingsw.network.message;


import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

//da completare
public class DisplayCemeteryMessage extends DisplayMessage{
    private static final long serialVersionUID = -7509713511298375285L;

    private final List<AssistantCardModel> cemetery;
    private final ObjectDisplay objectDisplay;

    /**
     *Message shown to display  player's cemetery
     * @param nickname the nickname of the player whom cemetery is displayed
     * @param cemetery the cemetery of the player
     */

    public DisplayCemeteryMessage(String nickname, List<AssistantCardModel> cemetery) {
        super(nickname);
        this.cemetery = cemetery;
        this.objectDisplay = ObjectDisplay.CEMETERY;
    }

    public List<AssistantCardModel> getCemetery() {
        return cemetery;
    }

    @Override
    public String toString() {
        return "ShowCemeteryMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", cemetery=" + cemetery +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
