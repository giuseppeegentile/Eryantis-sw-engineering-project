package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void showWinMessage(PlayerModel winner) {
        clientHandler.sendMessage(new WinMessage(winner));
    }

    @Override
    public void askPlayers() {

    }

    @Override
    public void askMoveMotherNature(int movement) {

    }

    @Override
    public void askTowerColor(int playersNumber) {

    }

    @Override
    public void askMoveCloudToEntrance(List<ColorPawns> students) {

    }

    @Override
    public void askMoveEntranceToHall(PlayerModel player,ColorPawns colorPawns) {
        clientHandler.sendMessage(new MoveMessage(player, colorPawns));
    }

    @Override
    public void askMoveEntranceToIsland(PlayerModel player,ColorPawns colorPawns, IslandModel islandModel) {
        clientHandler.sendMessage(new MoveMessage(player, colorPawns, islandModel));
    }

    @Override
    public void showDisconnectionMessage() {
        clientHandler.sendMessage(new DisconnectionMessage());

    }

    @Override
    public void showMoveMotherNatureMessage(PlayerModel player, IslandModel island, AssistantCardModel assistantCard) {
        clientHandler.sendMessage(new MoveMotherNatureMessage(player, island, assistantCard));

    }

    @Override
    public void showPlayAssistantCardMessage(PlayerModel player, AssistantCardModel assistantCard){
        clientHandler.sendMessage(new PlayAssistantCardMessage(player, assistantCard));
    }

    @Override
    public void showIslands() {

    }

    @Override
    public void showClouds() {

    }

    @Override
    public void showPlayerBoard(PlayerModel playerModel) {

    }

    @Override
    public void showCards(PlayerModel playerModel) {

    }

    @Override
    public void showOrderPhase() {

    }

    @Override
    public void askGetFromBag() {

    }

    @Override
    public void askShowClouds() {

    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String text) {

    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
