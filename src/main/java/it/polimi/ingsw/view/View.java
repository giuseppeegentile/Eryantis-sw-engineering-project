package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
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

    void askMoveCloudToEntrance(List<ColorPawns> students);

    void askMoveEntranceToHall(String player,List<ColorPawns> colorPawns);

    void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, IslandModel islandModel);

    void showHallMessage(String player, Map<ColorPawns, Integer> hall);

    void showEntranceMessage(String player, List<ColorPawns> entrance);

    //void showCards(PlayerModel playerModel);

    void showCemeteryMessage(String player, List<AssistantCardModel> cemetery);

    void showTextMessage(String player, String text);

    void showLobbyMessage(List<String> nicknameList);

    void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex);

    void showCloudsMessage(String nickname, List<CloudModel> clouds);

    void showMoveMotherNatureMessage(String player, byte movement);

    void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard);

    void showIslands(String nickname, List<IslandModel> islands);

    void showPlayerBoardMessage(String nickname, PlayerModel playerModel);

    void askGetFromBag();

    void showProfsMessage(String player, List<ColorPawns> profs);

    void showInvalidTower(String player, ColorTower colorTower);

    void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);

    void showTowerMessage(String player, ColorTower colorTower, int towerNumber);

    void showDeckMessage(String player, List<AssistantCardModel> playerDeck);

    void updateTowerOnIsland(String nickname, IslandModel islandModel);

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

    void askPlayCards(String nickname, List<AssistantCardModel> playerDeck);

    void showOrderPhase(String nickname, List<PlayerModel> order);
}
