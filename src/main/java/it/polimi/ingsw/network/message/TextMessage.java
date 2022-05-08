package it.polimi.ingsw.network.message;

public class TextMessage extends Message{
    private static final long serialVersionUID = 5073360700808368691L;

    private final String text;

    public TextMessage(String nickname, String text) {
        super(nickname, MessageType.TEXT);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "player=" + getNickname() +
                "text='" + text + '\'' +
                '}';
    }
}
