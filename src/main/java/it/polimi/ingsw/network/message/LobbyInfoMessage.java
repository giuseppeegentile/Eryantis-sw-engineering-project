package it.polimi.ingsw.network.message;

import java.util.List;

public class LobbyInfoMessage extends Message {
    private static final long serialVersionUID = -5041101920182206083L;
    private final List<String> nicknameList;

    /**
     * Message sent to display the players' list
     * Parameters are set by the constructor
     * @param nicknameList list of players
     */

    public LobbyInfoMessage(List<String> nicknameList) {
        super("Server_nickname", MessageType.LOBBY);
        this.nicknameList = nicknameList;
    }

    public List<String> getNicknameList() {
        return nicknameList;
    }

    @Override
    public String toString() {
        return "LobbyInfoMessage{" +
                "player=" + getNickname() +
                ", nicknameList=" + nicknameList +
                '}';
    }

}