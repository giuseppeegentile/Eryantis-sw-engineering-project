package it.polimi.ingsw.network.message;

public class DisplayMessage extends Message{

    private static final long serialVersionUID = -732631174905728965L;
    private ObjectDisplay objectDisplay;

    public DisplayMessage(String nickname) {
        super(nickname, MessageType.DISPLAY);

    }

    public ObjectDisplay getObjectDisplay() {
        return this.objectDisplay;
    }
}
