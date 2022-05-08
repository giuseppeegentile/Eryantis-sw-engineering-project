package it.polimi.ingsw.network.message;

import java.util.List;

public class LobbyInfoMessage extends Message {
    private static final long serialVersionUID = -5041101920182206083L;
    private final List<String> nicknameList;

    public LobbyInfoMessage(List<String> nicknameList) {
        super("Server_nickname", MessageType.LOBBY);
        this.nicknameList = nicknameList;
    }

    public List<String> getNicknameList() {
        return nicknameList;
    }

    @Override
    public String toString() {
        return "LobbyMessage{" +
                "nickname=" + getNickname() +
                ", nicknameList=" + nicknameList +
                '}';
    }

}