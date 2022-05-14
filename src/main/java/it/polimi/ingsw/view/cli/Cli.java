package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


//sout per fare printout

public class Cli extends ViewObservable implements View {

    private final PrintStream out;
    private Thread inputThread;

    private static final String STR_ROW = "Row: ";
    private static final String STR_COLUMN = "Column: ";
    private static final String STR_POSITION = "Position ";
    private static final String STR_INPUT_CANCELED = "User input canceled.";

    /**
     * Default constructor.
     */
    public Cli() {
        out = System.out;
    }

    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    public void showBoard(){

    }

    public void init() {
        out.println("" +
                        "888888 8888Yb 88    db    88b 88 888888 Yb  dP .dPY8 \n" +
                        "88__   88__dP 88   dPYb   88Yb88   88    YbdP  `Ybo. \n" +
                        "88     88 Yb  88  dP__Yb  88 Y88   88     8P   o.`Y8b \n" +
                        "888888 88  Yb 88 dP    Yb 88  Y8   88    dP    8bodP' \n");

        out.println("Welcome to Eriantys Board Game!");

        try {
            askServerInfo();   //il test si blocca, non capisco perché
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
        }
    }

    public void askServerInfo() throws ExecutionException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "16847";
        boolean validInput;

        out.println("Please specify the following settings. The default value is shown between brackets.");

        do {
            out.print("Enter the server address [" + defaultAddress + "]: ");

            String address = readLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (ClientController.isValidIpAddress(address)) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                out.println("Invalid address!");
                clearCli();
                validInput = false;
            }
        } while (!validInput);

        do {
            out.print("Enter the server port [" + defaultPort + "]: ");
            String port = readLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else {
                if (ClientController.isValidPort(port)) {
                    serverInfo.put("port", port);
                    validInput = true;
                } else {
                    out.println("Invalid port!");
                    validInput = false;
                }
            }
        } while (!validInput);

        notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));
    }


    @Override
    public void showWinMessage(PlayerModel winner) {
        out.println("Game finished: " + winner.getNickname() + " WINS!");
        System.exit(0);
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
    public void showClouds() {

    }

    @Override
    public void updateIslands(String nickname) {

    }

    @Override
    public void showPlayerBoard(PlayerModel playerModel) {

    }

    @Override
    public void showCards(PlayerModel playerModel) {
        int j = 0;
        int i = 0;
        out.println("These are your available assistant cards " + playerModel.getNickname() + "!\n");
        for (i = 0; i<playerModel.getDeckAssistantCardModel().size(); i++)
            if (!(playerModel.getDeckAssistantCardModel().get(i).getPriority() == 0 && playerModel.getDeckAssistantCardModel().get(i).getMotherNatureMovement() == 0)){
                out.println(j + " -> Priority = " + playerModel.getDeckAssistantCardModel().get(i).getPriority() + ", Mothernature Movements = " + playerModel.getDeckAssistantCardModel().get(i).getMotherNatureMovement() + "\n");
                j++;
        }
    }

    @Override
    public void askGetFromBag() {

    }

    @Override
    public void showProfsMessage(String player, List<ColorPawns> profs) {

    }

    @Override
    public void showInvalidTower(String player) {

    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clearCli();

        if (nicknameAccepted && connectionSuccessful) {
            out.println("Hi, " + nickname + "! You connected to the server.");
        } else if (connectionSuccessful) {
            askNickname();
        } else if (nicknameAccepted) {
            out.println("Max players reached. Connection refused.");
            out.println("EXIT.");

            System.exit(1);
        } else {
            showErrorAndExit("Could not contact server.");
        }
    }

    @Override
    public void showTowerMessage(String player, ColorTower colorTower, int towerNumber) {

    }

    @Override
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck) {

    }

    @Override
    public void updateTowerOnIsland(String nickname, IslandModel islandModel) {

    }


    @Override
    public void showEndTurn(String nick) {

    }

    @Override
    public void showStartTurn(String nick) {

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
        out.print("Enter your nickname: ");
        try {
            String nickname = readLine();
            notifyObserver(obs -> obs.onUpdateNickname(nickname));
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
        }
    }

    @Override
    public void showErrorAndExit(String error) {
        inputThread.interrupt();

        out.println("\nERROR: " + error);
        out.println("EXIT.");

        System.exit(1);
    }

    @Override
    public void askPlayCards(String nickname, List<AssistantCardModel> playerDeck) {

    }

    @Override
    public void showOrderPhase(String nickname, List<PlayerModel> order) {
        if(order.size() == 4)
            out.println("                           " + order.get(0).getNickname() + ", " + order.get(1).getNickname() + ", " + order.get(2).getNickname() + ", " + order.get(3).getNickname() + "\n");
        else if(order.size() == 3)
            out.println("                           " + order.get(0).getNickname() + ", " + order.get(1).getNickname() + ", " + order.get(2).getNickname() + "\n");
        else if(order.size() == 2)
            out.println("                           " + order.get(0).getNickname() + ", " + order.get(1).getNickname() + "\n");
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
    }


    /**
     * Clears the CLI terminal.
     */
    public void clearCli() {
        out.print(ColorCli.CLEAR);
        out.flush();
    }
}
