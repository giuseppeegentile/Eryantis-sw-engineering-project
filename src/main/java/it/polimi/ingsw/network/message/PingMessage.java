package it.polimi.ingsw.network.message;


public class PingMessage extends Message {

    private static final long serialVersionUID = -7019523659587734169L;

    /**
     * Shows the ping
     */

    public PingMessage() {
        super(null, MessageType.PING);
    }
}