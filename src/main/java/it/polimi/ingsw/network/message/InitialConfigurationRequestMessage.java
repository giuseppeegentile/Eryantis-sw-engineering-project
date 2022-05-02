package it.polimi.ingsw.network.message;

import com.google.gson.Gson;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
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

    public List<String> getPlayers() {
        return players;
    }

    public List<ColorTower> getTowers() {
        return towers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public String toString() {
        return "InitialConfigurationMessage{" +
                "players=" + players+
                ", towers=" + towers +
                ", mode=" + gameMode +
                '}';
    }
}
