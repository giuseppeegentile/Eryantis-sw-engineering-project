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
     * @param serverInfo a map of server address and server port.
     */
    void onUpdateServerInfo(Map<String, String> serverInfo);

    /**
     * Sends an initial message to the server for the configuration
     * @param nickname  The active player in the current turn
     * @param numPlayers The number of player of the current game
     * @param colorTower The color of tower chosen by the player
     * @param gameMode The mode of the game of the current game
     */
    void setGameBoard(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode);

    /**
     * Sends a message to the server when a player chooses a cloud
     * @param nickname The active player in the current turn
     * @param cloudIndex The index of cloud chosen
     */
    void onChosenCloud(String nickname, int cloudIndex);

    /**
     * Sends a message to the server when a player moves students from his entrance to the island
     * @param nickname The active player in the current turn
     * @param students The list of the students moved
     * @param indexIsland The index of the island where the player move the students
     */
    void onUpdateStudentToIsland(String nickname, List<ColorPawns> students, int indexIsland);

    /**
     * Sends a message to the server when a player moves students from his entrance to his hall
     * @param nickname The active player in the current turn
     * @param students The list of the students moved
     */
    void onUpdateStudentToHall(String nickname, List<ColorPawns> students);

    /**
     * Sends a message to the server when a player moves mother nature
     * @param player The active player in the current turn
     * @param movement The number of movements chosen by the player
     */
    void onUpdateMotherNature(String player, byte movement);

    /**
     * Sends a message to the server when a player chooses the assistant card for the current turn
     * @param playerModel The active player in the current turn
     * @param assistantCardModel The card chosen by the player
     */
    void onUpdateCardPlayed(String playerModel, int assistantCardModel);

    /**
     * Sends a message to the server when the first player chooses the number of players
     * @param playersNumber The number of players for the current match
     */
    void onUpdatePlayersNumber(int playersNumber);

    /**
     * Handles a disconnection caused by a user.
     */
    void onDisconnection();

    /**
     * Sends a message to the server when a player chooses a nickname
     * @param nickname The nickname chosen
     */
    void onUpdateNickname(String nickname);

    /**
     * Sends a message to the server when a player chooses the tower color
     * @param towerColor The tower color chosen
     */
    void onUpdateTower(ColorTower towerColor);

    /**
     * Sends a message to the server when the first player chooses the game mode of the current match
     * @param finalMode The game mode chosen
     */
    void onUpdateGameMode(GameMode finalMode);

    /**
     * Sends a message to the server when a player chooses a character card
     * @param activePlayer The active player in the current turn
     * @param chosenCard The character card played
     */
    void onUpdateCharacterCardPlayed(String activePlayer, CharacterCardModel chosenCard);

    /**
     * Sends a message to the server when a player uses the color to ignore effect
     * @param active The active player in the current turn
     * @param color Color to be ignored
     */
    void onUpdateColorToIgnore(String active, ColorPawns color);

    /**
     * Sends a message to the server when a player uses the move from card to island effect
     * @param active The active player in the current turn
     * @param indexIsland The index of the island where the student is placed
     * @param colorChosenIndex The index of the student chosen among those on the card
     */
    void onUpdateMovedStudentFromCardToIsland(String active, int indexIsland, ColorPawns colorChosenIndex);

    /**
     * Sends a message to the server when a player uses the extra get influence effect
     * @param active The active player in the current turn
     * @param indexIsland The island to calculate the influence
     */
    void onUpdateExtraGetInfluence(String active, int indexIsland);

    /**
     * Sends a message to the server when a player uses the ban card effect
     * @param active The active player in the current turn
     * @param indexIsland The island where the ban card is placed
     */
    void onUpdateBanCard(String active, int indexIsland);

    /**
     * Sends a message to the server when a player uses the move from card to entrance effect
     * @param active The active player in the current turn
     * @param studentsFromCard The list of students chosen from those on the card
     * @param studentsFromEntrance The list of students chosen from among in player's entrance
     */
    void onUpdateMovedStudentsFromCardToEntrance(String active, List<ColorPawns> studentsFromCard, List<ColorPawns> studentsFromEntrance);

    /**
     * Sends a message to the server when a player uses the move student from card to hall message
     * @param nickname The active player in the current turn
     * @param pickedStudent The student chosen among those on the card
     */
    void onMovedStudentsFromCardToHall(String nickname, ColorPawns pickedStudent);

    /**
     * Sends a message to the server when a player uses the color remove for all effect
     * @param active The active player in the current turn
     * @param equivalentColorPawns The color of student chosen
     */
    void onUpdateColorRemoveForAll(String active, ColorPawns equivalentColorPawns);

    /**
     * Sends a message to the server when a player uses the change hall entrance effect
     * @param active The active player in the current turn
     * @param studentsFromHall The list of students chosen among those in player's hall
     * @param studentsFromEntrance The list of students chosen among those in player's entrance
     */
    void onUpdateChangeHallEntrance(String active, List<ColorPawns> studentsFromHall, List<ColorPawns> studentsFromEntrance);

    /**
     * Sends a message to the server when a player need the lobby
     * @param nickname The active player in the current turn
     */
    void onRequestLobby(String nickname);

    /**
     * Sends a message to the server when a player needs to see someone other's board
     * @param nick The active player in the current turn
     * @param nickChosen
     */
    void onRequestBoard(String nick, String nickChosen);
}
