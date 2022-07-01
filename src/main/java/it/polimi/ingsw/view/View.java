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
     * Used when there is a need to show a generic message.
     * @param message The content of the message.
     */
    void showGenericMessage(String message);

    /**
     * Display a message notifying the client that he can't move that amount of player in a certain object.
     * @param nick The nickname that performed an illegal move.
     * @param movementAllowed The mother nature's movement the player can do.
     * @param movementInserted The mother nature's movement inserted by the player which is greater of what allowed.
     */
    void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted);

    /**
     * Display a message notifying the client that he can't move that amount of player in a certain object.
     * @param nickname The nickname that performed an illegal move.
     */
    void showInvalidNumberOfStudentMoved(String nickname);
    /**
     * Display the inputs for asking the nickname.
     */
    void askNickname();
    /**
     * Display an error page and restart the game.
     * @param error The error occurred.
     */
    void showErrorAndExit(String error);
    /**
     * Shows to the player the available cards he has.
     * @param nickname The nickname of the player that have to play the card.
     * @param playerDeck The available cards of the player. Not showing the one already used by other players.
     */
    void askPlayCard(String nickname, List<AssistantCardModel> playerDeck);

    /**
     * Shows the order of the players set by the assistant card priority for this turn.
     * @param nickname The nickname of the player that receive the message.
     * @param order The ordered list for the phase.
     */
    void showOrderPhase(String nickname, List<PlayerModel> order);

    /**
     * Display the inputs for asking the what of the available color's tower will have.
     * @param nickMessage The nickname of the player that will choose the tower color.
     * @param availableColorTowers List of available towers not chosen by other players yet.
     * @param gameMode The game mode previously chosen by the first player.
     */
    void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers, GameMode gameMode);

    /**
     * Display the inputs for asking how many player will join the server. Asked the first player who create the lobby.
     */
    void askPlayersNumber();

    /**
     * Display the inputs for asking the game mode to the first player who create the lobby.
     */
    void askGameMode();

    /**
     * Display the nicknames's board.
     * @param nickname The player that own all the next parameters.
     * @param towers The list of towers of the player.
     * @param hall The hall of the player.
     * @param entrance The entrance of the player.
     * @param profs The list of profs owned by the player.
     * @param isFirst True iff is the player who created the lobby or (in the 4-player-game) the "team leader", so the one who have all the towers. When false the player doesn't have towers, but the team is identified by the color of the towers.
     */
    void showPlayerBoardMessage(PlayerModel nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs, boolean isFirst);

    /**
     * Show a message saying skipping mother nature movement, this can be caused by an effect.
     * @param activeNick The player active, that will skip the mother nature movement.
     */
    void showSkippingMotherMovement(String activeNick);

    /**
     * Request to play a character card.
     * @param active The player active, that can play the card in this phase of the game.
     * @param characterDeck The available cards.
     * @param existsCardPlayable True iff the player has enough money to play at least a card from the deck.
     */
    void askPlayCharacterCard(PlayerModel active, List<CharacterCardModel> characterDeck, boolean existsCardPlayable);

    /**
     * Display the inputs for asking what player move from the card, and also in which island he wants to move.
     * @param active The player active, who played the character card in this turn.
     * @param islands The list of island that can be selected.
     * @param studentsOnCard The students over the card that can be moved on one island.
     */
    void askMoveStudentFromCardToIsland(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard);

    /**
     * Display the inputs for asking in which island the extra influence points will be assigned.
     * @param active The player active, who played the character card in this turn.
     * @param islands The list of island that can be selected.
     */
    void askExtraGetInfluence(String active, List<IslandModel> islands);

    /**
     * Display the inputs for asking in which island the prohibition card will be moved.
     * @param active The player active, who played the character card in this turn.
     * @param islands The list of island that will be updated after the effect.
     */
    void askMoveBanCard(String active, List<IslandModel> islands);

    /**
     * Display the inputs for asking what student move from the character card to the entrance.
     * @param active The player active, who played the card in this turn.
     * @param studentsOnCard The list of students over the card, to be moved to entrance.
     * @param entrance The list of students in entrance.
     */
    void askMoveFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance);

    /**
     * Display the inputs for asking the color to be ignored in influence algorithm.
     * @param active The player active, who played the card in this turn.
     */
    void askColorStudentToIgnore(String active);

    /**
     * Display the inputs for asking the color that all the players have to remove 3 students (of the selected color) from their entrance.
     * @param active The player active, who played the card in this turn.
     */
    void askColorRemoveForAll(String active);

    /**
     * Display the inputs for asking what students switch from the character hall to the entrance.
     * @param active The player active, who played the card in this turn.
     * @param entrance The list of students in entrance for this player to be switched.
     * @param hall The hall of the player to be switched.
     */
    void askStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall);

    /**
     * Display the inputs for asking what student move from the character card to the hall.
     * @param nickname The nickname that played the card.
     * @param studentsOnCard The list of students over the card.
     */
    void askStudentFromCardToHall(String nickname, List<ColorPawns> studentsOnCard);

    /**
     * Updates the view with the received entrance from the server.
     * @param nickname The nickname that owns the entrance.
     * @param studentInEntrance The list of students in entrance.
     */
    void showEntranceChange(String nickname, List<ColorPawns> studentInEntrance);
}
