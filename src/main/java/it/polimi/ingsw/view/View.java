package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;

import java.util.List;
import java.util.Map;

public interface View {

    /**
     * Display a message to the player who won the game
     * @param winner The winning player
     */
    void showWinMessage(PlayerModel winner);

    //void askNickname();

    /**
     * Display a message when 2 or more island are joined
     * @param message The message to send
     */
    void showMessageJoiningIsland(Message message);

    /**
     * Asks the active player to choose a cloud to move the students
     * @param nickname The active player of the current turn
     * @param clouds The list of available clouds
     */
    void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds);

    /**
     * Asks the active player to choose the students to move from the entrance to the hall
     * @param player The active player of the current turn
     * @param students The list of the students chosen
     * @param numberStudentsToMove The number of students to move
     */
    void askMoveEntranceToHall(String player,List<ColorPawns> students, int numberStudentsToMove);

    /**
     * Asks the active player to move mother nature
     * @param player The active player of the current turn
     * @param maxMovement The movement mother nature is allowed to do
     */
    void askMotherNatureMovements(PlayerModel player, byte maxMovement);

    /**
     * Asks the active player to choose the students to move from the entrance to an island
     * @param player The active player of the current turn
     * @param colorPawns The list of students chosen
     * @param islands The island where to move the students
     */
    void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, List<IslandModel> islands);

    //void showHallMessage(String player, Map<ColorPawns, Integer> hall);

    //void showEntranceMessage(String player, List<ColorPawns> entrance);

    //void showCards(PlayerModel playerModel);

    /**
     * Display a message with the cemetery of the current round
     * @param player The active player of the current turn
     * @param cemetery The list of the cards in the cemetery
     */
    void showCemeteryMessage(String player, List<AssistantCardModel> cemetery);

    /**
     * Display a generic text message
     * @param player The active player of the current turn
     * @param text The text of the message
     */
    void showTextMessage(String player, String text);

    /**
     * Display a message with the list of the nickname's players of the current game
     * @param nicknameList The list with the nicknames
     */
    void showLobbyMessage(List<PlayerModel> nicknameList);

    /**
     * Display the island modified in the current turn after mother nature is moved
     * @param nickname The active player of the current turn
     * @param islandModel The modified island
     * @param islandIndex The index of the modified island
     */
    void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex);

    /**
     * Dislpay all the available clouds to choose in the current turn
     * @param nickname The active player of the current turn
     * @param clouds The list of available clouds
     */
    void showCloudsMessage(String nickname, List<CloudModel> clouds);

    /**
     * Display a message with the mother nature's movements made by another player
     * @param player The active player of the current turn
     * @param movement The movement of mother nature
     */
    void showMoveMotherNatureMessage(PlayerModel player, byte movement);

    //void updateIslands(String nickname);

    //void askGetFromBag();

    /**
     * Display the islands when a player has to move students or when they're modified
     * @param nickname The active player of the current turn
     * @param islands The list of islands
     */
    void showIslands(String nickname, List<IslandModel> islands);

    //void showProfsMessage(String player, List<ColorPawns> profs);

    /**
     * Display a message when a player choose a not available tower color
     * @param player The active player of the current turn
     * @param colorTower The not available tower color
     */
    void showInvalidTower(String player, ColorTower colorTower);

    /**
     * Display a message when a player join the match with the corresponding result
     * @param nicknameAccepted True if the nickname chosen is not already taken
     * @param connectionSuccessful True if the player is connected to the server
     * @param nickname The nickname chosen by the player
     */
    void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);

    //void showTowerMessage(String player, ColorTower colorTower, int towerNumber);

    //void showDeckMessage(String player, List<AssistantCardModel> playerDeck);

    //void updateTowerOnIsland(String nickname, IslandModel islandModel);

    /**
     * Display a message when the turn of the active player is finished
     * @param nick The active player of the current turn
     */
    void showEndTurn(String nick);

    /**
     * Display a message when the nickname chosen by a player is already taken
     * @param nickname The nickname chosen by the player
     */
    void showInvalidNickname(String nickname);

    /**
     * Display a message when a new turn for a player is started
     * @param nick The active player of the current turn
     */
    void showStartTurn(String nick);

    /**
     * Display a message when a player chooses an empty or not valid cloud
     * @param nick The active player of the current turn
     */
    void showInvalidCloud(String nick);

    /**
     * Display a message when a player chooses a not available card
     * @param player The active player of the current turn
     * @param card The not available card
     */
    void errorCard(String player, AssistantCardModel card);

    /**
     * Display a message when a player disconnects from the server
     * @param nickname The player to show the message
     * @param message The content of the message
     */
    void showDisconnectionMessage(String nickname, String message);

    /**
     * Used when there is a need to show a generic message
     * @param message The content of the message
     */
    void showGenericMessage(String message);

    /**
     * Display a message when a player tries to move mother nature by a number of movements not allowed
     * @param nick The active player of the current turn
     * @param movementAllowed The number of movement the player can choose
     * @param movementInserted The number of movement chosen by the player
     */
    void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted);

    void showInvalidNumberOfStudentMoved(String nickname);

    void askNickname();

    void showErrorAndExit(String error);

    void askPlayCard(String nickname, List<AssistantCardModel> playerDeck);

    void showOrderPhase(String nickname, List<PlayerModel> order);

    void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers, GameMode gameMode);


    void askPlayersNumber();

    void askGameMode();

    void showPlayerBoardMessage(PlayerModel nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs, boolean isFirst);

    void showSkippingMotherMovement(String activeNick);

    void askPlayCharacterCard(PlayerModel active, List<CharacterCardModel> characterDeck, boolean existsCardPlayable);

    //da implementare nella cli
    void askMoveStudentFromCardToIsland(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard);

    void askExtraGetInfluence(String active, List<IslandModel> islands);

    void askMoveBanCard(String active, List<IslandModel> islands);

    void askMoveFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance);

    void askColorStudentToIgnore(String active);

    void askColorRemoveForAll(String active);

    void askStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall);

    void askStudentFromCardToHall(String nickname, List<ColorPawns> studentsOnCard);

    void showEntranceChange(String nickname, List<ColorPawns> studentInEntrance);
}
