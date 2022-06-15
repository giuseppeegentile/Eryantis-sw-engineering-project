package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

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
    public void askTowerColor(String nickMessage, List<ColorTower> availableTowers){
        clientHandler.sendMessage(new InitialResMessage(nickMessage, availableTowers));

    }

    @Override
    public void askGameMode(){
        clientHandler.sendMessage(new GameModeReq());
    }

    @Override
    public void showMessageJoiningIsland(Message message) {
        clientHandler.sendMessage(new TextMessage(message.getNickname(),((TextMessage)message).getText()));
    }

    @Override
    public void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds) {
        clientHandler.sendMessage(new ReqMoveCloudToEntranceMessage(nickname, clouds));
    }

    @Override
    public void showIslands(String nickname, List<IslandModel> islands){
        clientHandler.sendMessage(new DisplayIslandsMessage(nickname, islands));
    }

    @Override
    public void showPlayerBoardMessage(PlayerModel player, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs) {
        clientHandler.sendMessage(new DisplayPlayerBoardMessage(player, towers, hall, entrance, profs));
    }

    @Override
    public void askMoveEntranceToHall(String player,List<ColorPawns> colorPawns, int numberStudentsToMove) {
        clientHandler.sendMessage(new StudentToHallMessage(player, colorPawns, numberStudentsToMove));
    }

    @Override
    public void askMotherNatureMovements(PlayerModel player, byte maxMovement) {
        clientHandler.sendMessage(new ReqMoveMotherNatureMessage(player, maxMovement));
        //clientHandler.sendMessage(new MoveMotherNatureMessage(player, maxMovementAllowed));
    }

    @Override
    public void askMoveEntranceToIsland(String player,List<ColorPawns> colorPawns, List<IslandModel> islands) {
        clientHandler.sendMessage(new StudentToIslandMessage(player, colorPawns, islands));
    }

    /*@Override
    public void showEntranceMessage(String player, List<ColorPawns> entrance){
        clientHandler.sendMessage(new DisplayEntranceMessage(player, entrance));
    }*/

    /*public void showHallMessage(String player, Map<ColorPawns, Integer> hall){
        clientHandler.sendMessage(new DisplayHallMessage(player, hall));
    }*/

    @Override
    public void showDisconnectionMessage(String nickname, String message) {
        clientHandler.sendMessage(new DisconnectionMessage(nickname, message));
    }

    @Override
    public void showMoveMotherNatureMessage(PlayerModel player, byte movement) {
        clientHandler.sendMessage(new MovedMotherNatureMessage(player, movement));
    }

    @Override
    public void showCemeteryMessage(String player, List<AssistantCardModel> cemetery){
        clientHandler.sendMessage(new DisplayCemeteryMessage(player, cemetery));
    }

    @Override
    public void showTextMessage(String player, String text){
        clientHandler.sendMessage(new TextMessage(player, text));
    }

    @Override
    public void showLobbyMessage(List<String> nicknameList) {
        clientHandler.sendMessage(new LobbyInfoMessage(nicknameList));
    }

    @Override
    public void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {
        clientHandler.sendMessage(new DisplayIslandMessage(nickname, islandModel, islandIndex));
    }

    @Override
    public void showCloudsMessage(String nickname, List<CloudModel> clouds) {
        clientHandler.sendMessage(new DisplayCloudsMessage(nickname, clouds));
    }

    @Override
    public void askPlayCharacterCard(PlayerModel active, List<CharacterCardModel> characterDeck) {
        clientHandler.sendMessage(new ReqPlayCharacterCardMessage(active, characterDeck));
    }

    @Override
    public void askMoveStudentFromCardToIsland(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard) {
        clientHandler.sendMessage(new AskMoveStudentFromCardToIslandMessage(active, islands, studentsOnCard));
    }

    @Override
    public void askStudentFromCardToHall(String nickname, List<ColorPawns> studentsOnCard) {
        clientHandler.sendMessage(new ReqStudentFromCardToHall(nickname, studentsOnCard));
    }

    @Override
    public void showEntranceChange(String nickname, List<ColorPawns> studentInEntrance) {
        clientHandler.sendMessage(new EntranceChangeMessage(nickname, studentInEntrance));
    }

    @Override
    public void askExtraGetInfluence(String active, List<IslandModel> islands) {
        clientHandler.sendMessage(new AskExtraGetInfluenceMessage(active, islands));
    }

    @Override
    public void askMoveBanCard(String active, List<IslandModel> islands) {
        clientHandler.sendMessage(new AskMoveBanCardMessage(active, islands));
    }

    @Override
    public void askMoveFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance) {
        clientHandler.sendMessage(new AskMoveStudentsFromCardToEntrance(active, studentsOnCard, entrance));
    }

    @Override
    public void askColorStudentToIgnore(String active) {
        clientHandler.sendMessage(new AskColorToIgnore(active));
    }

    @Override
    public void askColorRemoveForAll(String active) {
        clientHandler.sendMessage(new AskColorRemoveFromAll(active));
    }

    @Override
    public void askStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall) {
        clientHandler.sendMessage(new AskStudentsChangeEntranceHall(active, entrance, hall));
    }

    /*@Override
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck) {
        clientHandler.sendMessage(new DisplayDeckAndAskCardMessage(player,playerDeck));
    }
    */
    //da mettere nel gameController
    @Override
    public void showOrderPhase(String nickname, List<PlayerModel> order){
        clientHandler.sendMessage(new OrderMessage(nickname, order));
    }

    @Override
    public void showSkippingMotherMovement(String activeNick) {
        clientHandler.sendMessage(new EffectSkippingInfluenceMessage(activeNick));
    }

    //--

    @Override
    public void showEndTurn(String nick){
        clientHandler.sendMessage(new EndTurnMessage(nick));
    }

    @Override
    public void showInvalidNickname(String nickname){
        clientHandler.sendMessage(new InvalidNicknameMessage(nickname));
    }

    @Override
    public void showStartTurn(String nick){
        clientHandler.sendMessage(new StartTurnMessage(nick));
    }

    @Override
    public void errorCard(String player, AssistantCardModel card){
        clientHandler.sendMessage(new ErrorCardMessageResponse(player, card));
    }

    @Override
    public void showInvalidTower(String player, ColorTower colorTower){
        clientHandler.sendMessage(new InvalidTowerMessage(player, colorTower));
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
    public void askNickname() {
        clientHandler.sendMessage(new LoginReply(false, true));
    }

    @Override
    public void showErrorAndExit(String error) {
        clientHandler.sendMessage(new ErrorMessage("Server_Nickname", error));
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

    @Override
    public void askPlayCard(String nickname, List<AssistantCardModel> playerDeck){
        clientHandler.sendMessage(new DisplayDeckAndAskCardMessage(nickname, playerDeck));
    }

    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new PlayerNumberRequest());
    }
}
