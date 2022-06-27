package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.CloudModel;

import java.util.List;

public class DisplayCloudsMessage extends DisplayMessage {

    private static final long serialVersionUID = 3791217817387328964L;

    private final List<CloudModel> clouds;
    private final ObjectDisplay objectDisplay;

    /**
     * Message sent to display the clouds
     * Parameters are set by the constructor
     * @param nickname nickname of the player's who is playing in that moment
     * @param clouds clouds shown
     */

    public DisplayCloudsMessage(String nickname, List<CloudModel> clouds) {
        super(nickname);
        this.clouds = clouds;
        this.objectDisplay = ObjectDisplay.CLOUDS;
    }

    /**
     * @return the list of clouds
     */

    public List<CloudModel> getClouds() {
        return clouds;
    }

    @Override
    public String toString() {
        return "DisplayCloudsMessage{" +
                "player=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", clouds=" + clouds +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
