package it.polimi.ingsw.network.message;

/**
 * Server to client.Message sent as text
 */

public class TextMessage extends Message{
    private static final long serialVersionUID = 5073360700808368691L;

    private final String text;

    /**
<<<<<<< HEAD
     * Text message sent
     * Parameters are set by the constructor
=======
     * Default constructor.
     * Parameters are set by the constructor.
>>>>>>> main
     * @param nickname current player
     * @param text text in the message
     */

    public TextMessage(String nickname, String text) {
        super(nickname, MessageType.TEXT);
        this.text = text;
    }

    /**
     * @return the text of the message
     */

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "player=" + getNickname() +
                ", text='" + text + '\'' +
                '}';
    }
}
