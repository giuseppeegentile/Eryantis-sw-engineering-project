package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        clientHandler.sendMessage(new DisplayIslandMessage(nickname, islandModel, islandIndex));
    }

    @Override
    public void commandMoveMotherNature(String player, byte movement) {
        clientHandler.sendMessage(new MoveMotherNatureMessage(player, movement));
    }

    @Override
    public void showMessageJoiningIsland(Message message) {
        clientHandler.sendMessage(new TextMessage(message.getNickname(),((TextMessage)message).getText()));

    }

    @Override
    public void askMoveCloudToEntrance(List<ColorPawns> students) {

    }

    @Override
    public void askMoveEntranceToHall(String player,List<ColorPawns> colorPawns) {
        clientHandler.sendMessage(new StudentToHallMessage(player, colorPawns));
    }

    @Override
    public void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, IslandModel islandModel) {
        int index = GameModel.getInstance().getIslandsModel().indexOf(islandModel);
        clientHandler.sendMessage(new StudentToIslandMessage(player, colorPawns,index));
    }

    @Override
    public void showHallMessage(String player, Map<ColorPawns, Integer> hall){
        clientHandler.sendMessage(new DisplayHallMessage(player, hall));
    }

    @Override
    public void showEntranceMessage(String player, List<ColorPawns> entrance){
        clientHandler.sendMessage(new DisplayEntranceMessage(player, entrance));
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
        clientHandler.sendMessage(new DisplayCloudsMessage("", GameModel.getInstance().getCloudsModel()));
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
    public void showTowerMessage(String player, ColorTower colorTower, int towerNumber) {
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
        clientHandler.sendMessage(new TextMessage(nick, "Your turn is over"));
    }

    @Override
    public void showStartTurn(String nick){
        clientHandler.sendMessage(new TextMessage(nick, "It's your turn"));
    }
    @Override
    public void updateCemetery(String nick, List<AssistantCardModel> cemetery){
        clientHandler.sendMessage(new DisplayCemeteryMessage(nick, cemetery));
    }

    @Override
    public void errorCard(String player, AssistantCardModel card){
        clientHandler.sendMessage(new ErrorCardMessageResponse(player, card));
    }

    @Override
    public void showInvalidTower(String player){
        clientHandler.sendMessage(new InvalidTowerMessage(player));
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clientHandler.sendMessage(new LoginReply(nicknameAccepted, connectionSuccessful));
    }

    @Override
    public void showGenericMessage(String message){
        clientHandler.sendMessage(new GenericMessage(message));
    }

    @Override
    public void showInvalidNumberOfStudentMoved(String nickname){
        clientHandler.sendMessage(new InvalidNumberStudentsMovedMessage(nickname));
    }

    @Override
    public void showInvalidCloud(String nick){
        clientHandler.sendMessage(new TextMessage(nick, "Choose a valid cloud"));
    }


    @Override
    public void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted){
        clientHandler.sendMessage(new InvalidMovementMessage(nick, movementAllowed, movementInserted));
    }
    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
