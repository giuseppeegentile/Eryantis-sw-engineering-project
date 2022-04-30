package it.polimi.ingsw.network.message;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.List;

public class InitialConfigurationRequestMessage extends Message {
    private static final long serialVersionUID = 253192915029656524L;

    private final List<String> players;
    private final List<ColorTower> towers;
    private final GameMode gameMode;

    public InitialConfigurationRequestMessage(List<String> players, List<ColorTower> towers, GameMode gameMode){
        super(players.get(0), MessageType.PLAYERS_REQUEST);
        this.gameMode = gameMode;
        this.players = players;
        this.towers = towers;
    }

    @Override
    public String toString() {
        return "InitialConfigurationMessage{" +
                "players=" + new Gson().toJson(players)+
                ", towers=" + new Gson().toJson(towers) +
                ", mode=" + gameMode +
                '}';
    }
}
