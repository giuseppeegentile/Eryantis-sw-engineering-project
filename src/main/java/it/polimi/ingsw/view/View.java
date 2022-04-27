package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public interface View {
    void showWinMessage(PlayerModel winner);

    void askPlayers();

    void askMoveMotherNature(int movement);

    void askTowerColor(int playersNumber);

    void askMoveCloudToEntrance(List<ColorPawns> students);

    void askMoveEntranceToHall(PlayerModel player,ColorPawns colorPawns);

    void askMoveEntranceToIsland(PlayerModel player,ColorPawns colorPawns, IslandModel islandModel);

    void showDisconnectionMessage();

    void showMoveMotherNatureMessage(PlayerModel player, IslandModel island, AssistantCardModel assistantCard);

    void showPlayAssistantCardMessage(PlayerModel player, AssistantCardModel assistantCard);

    void showIslands();

    void showClouds();

    void showPlayerBoard(PlayerModel playerModel);

    void showCards(PlayerModel playerModel);

    void showOrderPhase();

    void askGetFromBag();

    void askShowClouds();

    void showDisconnectionMessage(String nicknameDisconnected, String text);
}
