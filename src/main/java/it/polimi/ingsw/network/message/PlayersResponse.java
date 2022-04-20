package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class PlayersResponse extends Message{

    private static final long serialVersionUID = -4419241297635925047L;
    private final List<PlayerModel> players;

    public PlayersResponse(String nickname, List<PlayerModel> players) {
        super(nickname, MessageType.PLAYERS_RESPONSE);
        this.players = players;

    }

    public List<PlayerModel> getPlayers() {
        return this.players;
    }

    @Override
    public String toString() {
        StringBuilder strToBuild = new StringBuilder();
        for(int i = 0; i < players.size(); i++){
            strToBuild.append("player").append(i).append("=").append(players.get(i).getNickname());
            strToBuild.append("tower").append(i).append("=").append(players.get(i).getColorTower());
        }

        return "PlayersResponse{" +
                "nickname=" + getNickname() +
                ", " + strToBuild +
                '}';
    }
}
