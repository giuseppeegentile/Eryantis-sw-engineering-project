package it.polimi.ingsw.network.message;

public class PlayingMessage extends  Message{
    private static final long serialVersionUID = 3406990350116495619L;

    private final String nicknameActive;

    /**
     * Message sent to show the current player
     * @param nickname current player
     * @param nicknameActive active player
     */
    public PlayingMessage(String nickname, String nicknameActive) {
        super(nickname, MessageType.WHO_PLAY);
        this.nicknameActive = nicknameActive;
    }

    public String getNicknameActive() {
        return nicknameActive;
    }

    @Override
    public String toString() {
        return "PlayingMessage{" +
                "player=" + getNickname() +
                ", nicknameActive=" + nicknameActive + '\'' +
                '}';
    }
}
