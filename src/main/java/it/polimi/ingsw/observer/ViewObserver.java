package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
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
     * Sends an initial message to the server for the configuration
     *
     * @param nickname the player to be sent.
     * @param numPlayers the number of player to be sent.
     * @param colorTower the color of tower chosen by the player to be sent.
     * @param gameMode the mode of the game to be sent.
     */
    void onUpdateInitialConfiguration(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode);

    void onUpdateStudentToIsland(String nickname, List<ColorPawns> students, int indexIsland);

    void onUpdateStudentToHall(String nickname, List<ColorPawns> students);

    void onUpdateMotherNature(String player, byte movement);

    void onUpdateWaiting(String nickname, int cloudIndex);

    void onCardPlayed(String playerModel, AssistantCardModel assistantCardModel);

    /**
     * Handles a disconnection desired by the user.
     */
    void onDisconnection();
}
