package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.HashMap;
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
    public void askNickname() {
        clientHandler.sendMessage(new LoginReply(false, true));
    }

    @Override
    public void showNewIsland(String nickname, IslandModel islandModel, int islandIndex){

    }

    @Override
    public void commandMoveMotherNature(String player, byte movement) {
        clientHandler.sendMessage(new MoveMotherNatureMessage(player, movement));
    }

    @Override
    public void askMoveCloudToEntrance(List<ColorPawns> students) {

    }

    @Override
    public void askMoveEntranceToHall(String player,ColorPawns colorPawns) {
        clientHandler.sendMessage(new MoveMessage(player, colorPawns));
    }

    @Override
    public void askMoveEntranceToIsland(String player,ColorPawns colorPawns, IslandModel islandModel) {
        clientHandler.sendMessage(new MoveMessage(player, colorPawns, islandModel));
    }

    @Override
    public void showDisconnectionMessage() {
        clientHandler.sendMessage(new DisconnectionMessage());

    }

    @Override
    public void showMoveMotherNatureMessage(String player, byte movement) {
        clientHandler.sendMessage(new MoveMotherNatureMessage(player, movement));

    }

    @Override
    public void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard){
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
    public void showDisconnectionMessage(String nicknameDisconnected, String text) {

    }

    @Override
    public void showInitialTowerMessage(String player, ColorTower colorTower, int towerNumber) {
        clientHandler.sendMessage(new TowerMessage(player, colorTower, towerNumber));
    }

    @Override
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck) {
        clientHandler.sendMessage(new AssignPlayerDeckResponseMessage(player,playerDeck));
    }

    @Override
    public void showNewHall(String nickname, HashMap<ColorPawns, Integer> hall) {

    }
    @Override
    public void updateTowerOnIsland(IslandModel islandModel){

    }

    @Override
    public void updateTowerOnBoard(String nickname, int towerNumber){

    }

    @Override
    public void updateIslands(List<IslandModel> islandModel){

    }

    @Override
    public void showEndTurn(String nick){

    }

    @Override
    public void showStartTurn(String nick){

    }
    @Override
    public void updateCemetery(AssistantCardModel card){

    }

    @Override
    public void errorCard(String player, AssistantCardModel card){
        clientHandler.sendMessage(new ErrorCardMessageResponse(player, card));
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
