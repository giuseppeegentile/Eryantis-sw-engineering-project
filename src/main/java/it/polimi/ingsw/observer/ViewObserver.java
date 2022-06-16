package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

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
    void setGameBoard(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode);

    void onChosenCloud(String nickname, int cloudIndex);

    void onUpdateStudentToIsland(String nickname, List<ColorPawns> students, int indexIsland);

    void onUpdateStudentToHall(String nickname, List<ColorPawns> students);

    void onUpdateMotherNature(String player, byte movement);

    void onUpdateWaiting(String nickname, int cloudIndex);

    void onUpdateCardPlayed(String playerModel, int assistantCardModel);

    void onUpdatePlayersNumber(int playersNumber);
    /**
     * Handles a disconnection desired by the user.
     */
    void onDisconnection();

    void onUpdateNickname(String nickname);

    void onUpdateInitialConfig(String nickname, int numberPlayers, ColorTower chosenTower, GameMode mode);

    void onUpdateTower(ColorTower towerColor);

    void onUpdateGameMode(GameMode finalMode);

    void onUpdateCharacterCardPlayed(String activePlayer, CharacterCardModel chosenCard);

    void onUpdateColorToIgnore(String active, ColorPawns color);

    void onUpdateMovedStudentFromCardToIsland(String active, int indexIsland, ColorPawns colorChosenIndex);

    void onUpdateExtraGetInfluence(String active, int indexIsland);

    void onUpdateBanCard(String active, int indexIsland);

    void onUpdateMovedStudentsFromCardToEntrance(String active, List<ColorPawns> studentsFromCard, List<ColorPawns> studentsFromEntrance);

    void onMovedStudentsFromCardToHall(String nickname, ColorPawns pickedStudent);

    void onUpdateColorRemoveForAll(String active, ColorPawns equivalentColorPawns);

    void onUpdateChangeHallEntrance(String active, List<ColorPawns> studentsFromHall, List<ColorPawns> studentsFromEntrance);

    void onRequestLobby(String nickname);

    void onRequestBoard(String nick, String nickChosen);
}
