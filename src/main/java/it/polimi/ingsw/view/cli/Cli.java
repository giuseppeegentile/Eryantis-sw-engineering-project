package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Map;

import static java.lang.System.out;

//sout per fare printout

public class Cli extends ViewObservable implements View {

    @Override
    public void showWinMessage(PlayerModel winner) {

    }

    @Override
    public void commandMoveMotherNature(String player, byte movement) {

    }

    @Override
    public void showMessageJoiningIsland(Message message) {

    }

    @Override
    public void askMoveCloudToEntrance(List<ColorPawns> students) {

    }

    @Override
    public void askMoveEntranceToHall(String player, List<ColorPawns> colorPawns) {

    }

    @Override
    public void askMoveEntranceToIsland(String player, List<ColorPawns> colorPawns, IslandModel islandModel) {

    }

    @Override
    public void showHallMessage(String player, Map<ColorPawns, Integer> hall) {

    }

    @Override
    public void showEntranceMessage(String player, List<ColorPawns> entrance) {

    }

    @Override
    public void showCemeteryMessage(String player, List<AssistantCardModel> cemetery) {

    }

    @Override
    public void showTextMessage(String player, String text) {

    }

    @Override
    public void showLobbyMessage(List<String> nicknameList) {

    }

    @Override
    public void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {

    }

    @Override
    public void showCloudsMessage(String nickname, List<CloudModel> clouds) {

    }

    @Override
    public void showMoveMotherNatureMessage(String player, byte movement) {

    }

    @Override
    public void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard) {

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
    public void showProfs(String player, List<ColorPawns> profs) {

    }

    @Override
    public void showInvalidTower(String player) {

    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {

    }

    @Override
    public void showTowerMessage(String player, ColorTower colorTower, int towerNumber) {

    }

    @Override
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck) {

    }

    @Override
    public void showNewIsland(String nickname, IslandModel islandModel, int islandIndex) {

    }

    @Override
    public void updateTowerOnIsland(IslandModel islandModel) {

    }

    @Override
    public void updateTowerOnBoard(String nickname, int towerNumber) {

    }

    @Override
    public void updateIslands(List<IslandModel> islandModel) {

    }

    @Override
    public void showEndTurn(String nick) {

    }

    @Override
    public void showStartTurn(String nick) {

    }

    @Override
    public void updateCemetery(String nick, List<AssistantCardModel> cemetery) {

    }

    @Override
    public void showInvalidCloud(String nick) {

    }

    @Override
    public void errorCard(String player, AssistantCardModel card) {

    }

    @Override
    public void showDisconnectionMessage(String nickname, String message) {

    }

    @Override
    public void showGenericMessage(String message) {

    }

    @Override
    public void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted) {

    }

    @Override
    public void showInvalidNumberOfStudentMoved(String nickname) {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void showError(String error) {

    }

    /**
     * Shows the lobby screen on the terminal.
     *
     * @param nicknameList list of players.
     */
    @Override
    public void showLobbyMessage(List<String> nicknameList) {
        out.println("LOBBY:");
        for (String nick : nicknameList) {
            out.println(nick + "\n");
        }
        out.println("Current players in lobby: " + nicknameList.size() + " / " + numPlayers);
    }


    /**
     * Clears the CLI terminal.
     */
    public void clearCli() {
        out.print(ColorCli.CLEAR);
        out.flush();
    }
}
