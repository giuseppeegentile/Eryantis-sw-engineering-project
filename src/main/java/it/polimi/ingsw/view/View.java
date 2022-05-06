package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface View {
    void showWinMessage(PlayerModel winner);

    void askNickname();

    void commandMoveMotherNature(String player, byte movement);

    void showMessageJoiningIsland(Message message);

    void askMoveCloudToEntrance(List<ColorPawns> students);

    void askMoveEntranceToHall(String player,List<ColorPawns> colorPawns);

    void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, IslandModel islandModel);

    void showHallMessage(String player, Map<ColorPawns, Integer> hall);

    void showEntranceMessage(String player, List<ColorPawns> entrance);

    void showDisconnectionMessage();

    void showMoveMotherNatureMessage(String player, byte movement);

    void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard);

    void showIslands();

    public void showClouds();

    void showPlayerBoard(PlayerModel playerModel);

    void showCards(PlayerModel playerModel);

    void showOrderPhase();

    void askGetFromBag();

    void showInvalidTower(String player);

    void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);

    void showTowerMessage(String player, ColorTower colorTower, int towerNumber);

    void showDeckMessage(String player, List<AssistantCardModel> playerDeck);

    void showNewIsland(String nickname, IslandModel islandModel, int islandIndex);

    void showNewHall(String nickname, HashMap<ColorPawns, Integer> hall);

    void updateTowerOnIsland(IslandModel islandModel);

    void updateTowerOnBoard(String nickname, int towerNumber);

    void updateIslands(List<IslandModel> islandModel);

    void showEndTurn(String nick);

    void showStartTurn(String nick);

    void updateCemetery(String nick, List<AssistantCardModel> cemetery);

    void showInvalidCloud(String nick);

    void errorCard(String player, AssistantCardModel card);

    void showDisconnectionMessage(String nicknameDisconnected, String text);

    void showGenericMessage(String message);

    void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted);

    void showInvalidNumberOfStudentMoved(String nickname);
}
