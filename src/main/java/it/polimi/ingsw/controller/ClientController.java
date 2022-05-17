package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements ViewObserver, Observer {
    private final View view;
    private Client client;
    private String nickname;
    public static final int UNDO_TIME = 5000;
    private final ExecutorService queueTasks;

    public ClientController(View view){
        this.view = view;
        this.queueTasks = Executors.newSingleThreadExecutor();
    }


    /**
     * Takes action based on the message type received from the server. Destination: client, who's going to change the view.
     *
     * @param message the message from the server.
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case WIN:
                WinMessage winMessage = (WinMessage)message;
                queueTasks.execute(() -> view.showWinMessage(winMessage.getWinner()));
                break;
            case INIT:
                queueTasks.execute(()->view.askTowerColor(message.getNickname(), ((InitialResMessage)message).getAvailableTowers()));
                break;
            case GAMEMODE_REQUEST:
                queueTasks.execute(view::askGameMode);
                break;
            case PLAYERNUMBER_REQUEST:
                queueTasks.execute(view::askPlayersNumber);
                break;
            case DISCONNECTION:
                client.disconnect();
                queueTasks.execute(() -> view.showDisconnectionMessage(message.getNickname(), ((DisconnectionMessage) message).getMessageStr()));
                break;
            case ERROR:
                queueTasks.execute(() -> view.showErrorAndExit(((ErrorMessage)message).getError()));
                break;
            case TEXT:
                TextMessage textMessage = (TextMessage)message;
                queueTasks.execute(() -> view.showTextMessage(textMessage.getNickname(), textMessage.getText()));
                break;
            case ORDER:
                OrderMessage orderMessage = (OrderMessage)message;
                queueTasks.execute(() -> view.showOrderPhase(orderMessage.getNickname(), orderMessage.getOrder()));
                break;
            case LOBBY:
                LobbyInfoMessage lobbyMessage = (LobbyInfoMessage)message;
                queueTasks.execute(() -> view.showLobbyMessage(lobbyMessage.getNicknameList()));
                break;
            case DISPLAY:
                ObjectDisplay objectDisplay =((DisplayMessage) message).getObjectDisplay();
                switch (objectDisplay){
                    case ISLAND:
                        DisplayIslandMessage displayIsland = (DisplayIslandMessage)message;
                        queueTasks.execute(() -> view.showIslandMessage(displayIsland.getNickname(), displayIsland.getIslandModel(), displayIsland.getIslandIndex()));
                        break;
                    case PROF:
                        DisplayProfMessage displayProfMessage = (DisplayProfMessage)message;
                        queueTasks.execute(() -> view.showProfsMessage(displayProfMessage.getNickname(), displayProfMessage.getProfs()));
                        break;
                    case CLOUDS:
                        DisplayCloudsMessage displayClouds = (DisplayCloudsMessage)message;
                        queueTasks.execute(() -> view.showCloudsMessage(displayClouds.getNickname(), displayClouds.getClouds()));
                        break;
                    case DECK:
                        DisplayDeckMessage displayDeckMessage = (DisplayDeckMessage)message;
                        queueTasks.execute(() -> view.showDeckMessage(displayDeckMessage.getNickname(), displayDeckMessage.getDeck()));
                        break;
                    case HALL:
                        DisplayHallMessage displayHall = (DisplayHallMessage)message;
                        queueTasks.execute(() -> view.showHallMessage(displayHall.getNickname(), displayHall.getHall()));
                        break;
                    case ENTRANCE:
                        DisplayEntranceMessage displayEntrance = (DisplayEntranceMessage)message;
                        queueTasks.execute(() -> view.showEntranceMessage(displayEntrance.getNickname(), displayEntrance.getEntrance()));
                        break;
                    case CEMETERY:
                        DisplayCemeteryMessage displayCemetery = (DisplayCemeteryMessage)message;
                        queueTasks.execute(() -> view.showCemeteryMessage(displayCemetery.getNickname(), displayCemetery.getCemetery()));
                        break;
                }

                break;
            case GENERIC_MESSAGE:
                queueTasks.execute(() -> view.showGenericMessage(message.toString()));
                break;
            case LOGIN_REPLY:
                queueTasks.execute(() -> view.showLoginResult(((LoginReply) message).isNicknameAccepted(), ((LoginReply) message).isConnectionSuccessful(), this.nickname));
                break;
            case START_TURN:
                queueTasks.execute(() -> view.showStartTurn(message.getNickname()));
                break;
            case END_TURN:
                queueTasks.execute(() -> view.showEndTurn(message.getNickname()));
                break;
        }
    }

    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberReply(this.nickname, playersNumber));
    }

    @Override
    public void onUpdateGameMode(GameMode gameMode) {
        client.sendMessage(new GameModeRes(this.nickname, gameMode));
    }

    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {
        try {
            client = new SocketClient(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage(); // Starts an asynchronous reading from the server.
            client.enablePinger(true);
            queueTasks.execute(view::askNickname); //askInitialConfig
        } catch (IOException e) {
            queueTasks.execute(() -> view.showLoginResult(false, false, this.nickname));
        }
    }

    //client sends messages to the server when a player wants to perform some actions

    @Override
    public void setGameBoard(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode) {
        this.nickname = nickname;
        client.sendMessage(new PlayerNicknameMessage(this.nickname, numPlayers, colorTower, gameMode));
    }

    @Override
    public void onChosenCloud(String nickname, int cloudIndex){
        client.sendMessage(new AddStudentFromCloudToWaitingMessage(nickname, cloudIndex));
    }

    @Override
    public void onUpdateStudentToIsland(String nickname, List<ColorPawns> students, int indexIsland){
        client.sendMessage(new StudentToIslandMessage(nickname, students, indexIsland));
    }

    @Override
    public void onUpdateStudentToHall(String nickname, List<ColorPawns> students){
        client.sendMessage(new StudentToHallMessage(nickname, students));
    }

    @Override
    public void onUpdateMotherNature(String player, byte movement){
        client.sendMessage(new MoveMotherNatureMessage(nickname, movement));
    }

    @Override
    public void onUpdateWaiting(String nickname, int cloudIndex){
        client.sendMessage(new AddStudentFromCloudToWaitingMessage(nickname, cloudIndex));
    }

    @Override
    public void onUpdateCardPlayed(String nickname, AssistantCardModel assistantCardModel){
        client.sendMessage(new PlayAssistantCardMessage(nickname, assistantCardModel));
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(nickname));
    }

    @Override
    public void onUpdateInitialConfig(String nickname, int numberPlayers, ColorTower colorTowerStr, GameMode gameMode) {
        client.sendMessage(new InitialReqMessage(nickname, numberPlayers, colorTowerStr,gameMode));
    }

    @Override
    public void onUpdateTower(ColorTower towerColor) {
        client.sendMessage(new ChosenTowerMessage(this.nickname, towerColor));
    }

    @Override
    public void onDisconnection() {
        client.disconnect();
    }

    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
     * Checks if the given port string is in the range of allowed ports.
     *
     * @param portStr the ports to be checked.
     * @return {@code true} if the port is valid, {@code false} otherwise.
     */
    public static boolean isValidPort(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


