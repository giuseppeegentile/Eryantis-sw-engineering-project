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
     * Takes action based on the message type received from the server.
     *
     * @param message the message from the server.
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case WIN:
                break;
            case DISCONNECTION:
                client.disconnect();
                view.showDisconnectionMessage(message.getNickname(), ((DisconnectionMessage) message).getMessageStr());
                break;
            case ERROR:
                view.showError(((ErrorMessage)message).getError());
                break;
            case TEXT:
                break;
            case LOBBY:
                break;
            case DISPLAY:
                break;
            case GENERIC_MESSAGE:
                break;
            case LOGIN_REPLY:
                LoginReply loginReply = (LoginReply) message;
                queueTasks.execute(() -> view.showLoginResult(loginReply.isNicknameAccepted(), loginReply.isConnectionSuccessful(), this.nickname));
                break;
        }
    }

    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {
        try {
            client = new SocketClient(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage(); // Starts an asynchronous reading from the server.
            client.enablePinger(true);
            queueTasks.execute(view::askNickname);
        } catch (IOException e) {
            queueTasks.execute(() -> view.showLoginResult(false, false, this.nickname));
        }
    }

    @Override
    public void onUpdateInitialConfiguration(String nickname, int numPlayers, ColorTower colorTower, GameMode gameMode) {
        this.nickname = nickname;
        client.sendMessage(new PlayerNicknameMessage(this.nickname, numPlayers, colorTower, gameMode));
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
    public void onCardPlayed(String nickname, AssistantCardModel assistantCardModel){
        client.sendMessage(new PlayAssistantCardMessage(nickname, assistantCardModel));
    }

    @Override
    public void onDisconnection() {

    }
}
