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
    void showWinMessage(PlayerModel winner);

    //void askNickname();

    void showMessageJoiningIsland(Message message);

    void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds);

    void askMoveEntranceToHall(String player,List<ColorPawns> students, int numberStudentsToMove);

    void askMotherNatureMovements(PlayerModel player, byte maxMovement);

    void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, List<IslandModel> islands);

    //void showHallMessage(String player, Map<ColorPawns, Integer> hall);

    //void showEntranceMessage(String player, List<ColorPawns> entrance);

    //void showCards(PlayerModel playerModel);

    void showCemeteryMessage(String player, List<AssistantCardModel> cemetery);

    void showTextMessage(String player, String text);

    void showLobbyMessage(List<String> nicknameList);

    void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex);

    void showCloudsMessage(String nickname, List<CloudModel> clouds);

    void showMoveMotherNatureMessage(PlayerModel player, byte movement);

    //void updateIslands(String nickname);

    //void askGetFromBag();

    void showIslands(String nickname, List<IslandModel> islands);

    //void showProfsMessage(String player, List<ColorPawns> profs);

    void showInvalidTower(String player, ColorTower colorTower);

    void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);

    //void showTowerMessage(String player, ColorTower colorTower, int towerNumber);

    //void showDeckMessage(String player, List<AssistantCardModel> playerDeck);

    //void updateTowerOnIsland(String nickname, IslandModel islandModel);

    void showEndTurn(String nick);

    void showInvalidNickname(String nickname);

    void showStartTurn(String nick);

    void showInvalidCloud(String nick);

    void errorCard(String player, AssistantCardModel card);

    void showDisconnectionMessage(String nickname, String message);

    void showGenericMessage(String message);

    void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted);

    void showInvalidNumberOfStudentMoved(String nickname);

    void askNickname();

    void showErrorAndExit(String error);

    void askPlayCard(String nickname, List<AssistantCardModel> playerDeck);

    void showOrderPhase(String nickname, List<PlayerModel> order);

    void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers, GameMode gameMode);


    void askPlayersNumber();

    void askGameMode();

    void showPlayerBoardMessage(PlayerModel nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs);

    void showSkippingMotherMovement(String activeNick);

    void askPlayCharacterCard(PlayerModel active, List<CharacterCardModel> characterDeck);

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
