package it.polimi.ingsw.network.message;

/**
 * Message sent when a player disconnects from the game. Sent to all other players that are still in the game.
 */
public class DisconnectionMessage extends Message {
    private static final long serialVersionUID = -5422965079989607600L;


    private final String nicknameDisconnected;
    private final String messageStr;

    /**
     *
     * @param nicknameDisconnected nickname of the player who disconnected
     * @param messageStr text message shown
     */

    public DisconnectionMessage(String nicknameDisconnected, String messageStr) {
        super("Server_nickname", MessageType.DISCONNECTION);
        this.nicknameDisconnected = nicknameDisconnected;
        this.messageStr = messageStr;
    }

    /**
     *
     * @return the player who's disconnected.
     */
    public String getNicknameDisconnected() {
        return nicknameDisconnected;
    }

    /**
     * @return the message's text. Saying who disconnected.
     */
    public String getMessageStr() {
        return messageStr;
    }

    @Override
    public String toString() {
        return "DisconnectionMessage{" +
                "player='" + nicknameDisconnected + '\'' +
                ", messageStr='" + messageStr + '\'' +
                '}';
    }
}
