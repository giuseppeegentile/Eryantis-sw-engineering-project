package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
import java.util.Map;

/**
 * Custom observer interface for views. It supports different types of notification.
 */
public interface ViewObserver {

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onUpdateServerInfo(Map<String, String> serverInfo);

    /**
     * Sends a message to the server with the updated players.
     *
     * @param players the players to be sent.
     */
    void onUpdatePlayers(List<PlayerModel> players);

    /**
     * Sends a message to the server with the tower colors chosen by the players.
     *
     * @param colorTower the color of the towers.
     */
    void onUpdateWorkersColor(ColorTower colorTower);


    /**
     * Sends a message to the server with the mode of the game choosen.
     *
     * @param gameMode the mode of the game.
     */
    void onUpdateGameMode(GameMode gameMode);

    /**
     * Handles a disconnection wanted by the user.
     * (e.g. a click on the back button into the GUI).
     */
    void onDisconnection();
}
