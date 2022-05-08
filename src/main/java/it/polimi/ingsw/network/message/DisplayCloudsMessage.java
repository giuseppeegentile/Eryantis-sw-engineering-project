package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.CloudModel;

import java.util.List;

public class DisplayCloudsMessage extends Message {

    private static final long serialVersionUID = 3791217817387328964L;

    private final List<CloudModel> clouds;
    private final ObjectDisplay objectDisplay;

    public DisplayCloudsMessage(String nickname, List<CloudModel> clouds) {
        super(nickname, MessageType.DISPLAY);
        this.clouds = clouds;
        this.objectDisplay = ObjectDisplay.CLOUDS;
    }

    public List<CloudModel> getClouds() {
        return clouds;
    }

    @Override
    public String toString() {
        return "CloudsMessage{" +
                "player=" + getNickname() +
                "objectDisplay=" + getObjectDisplay() +
                "clouds=" + clouds +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }
}
