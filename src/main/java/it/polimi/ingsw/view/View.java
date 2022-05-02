package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.MessageType;

import java.util.HashMap;
import java.util.List;

public interface View {
    void showWinMessage(PlayerModel winner);

    void askPlayers();

    void askMoveMotherNature(int movement);

    void askTowerColor(int playersNumber);

    void askMoveCloudToEntrance(List<ColorPawns> students);

    void askMoveEntranceToHall(String player,ColorPawns colorPawns);

    void askMoveEntranceToIsland(String player,ColorPawns colorPawns, IslandModel islandModel);

    void showDisconnectionMessage();

    void showMoveMotherNatureMessage(String player, byte movement);

    void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard);

    void showIslands();

    void showClouds();

    void showPlayerBoard(PlayerModel playerModel);

    void showCards(PlayerModel playerModel);

    void showOrderPhase();

    void askGetFromBag();

    void askShowClouds();

    void showInitialMessage(PlayerModel player, List<AssistantCardModel> playerDeck, ColorTower colorTower, int towerNumber);

    void showNewIsland(String nickname, IslandModel islandModel, int islandIndex);

    void showNewHall(String nickname, HashMap<ColorPawns, Integer> hall);

    void updateTowerOnIsland(IslandModel islandModel);

    void updateTowerOnBoard(String nickname, int towerNumber);

    void updateIslands(List<IslandModel> islandModel);

    void showEndTurn(String nick);

    void showStartTurn(String nick);

    void updateCemetery(AssistantCardModel card);

    void errorCard(String player, AssistantCardModel card);

    void showDisconnectionMessage(String nicknameDisconnected, String text);
}
