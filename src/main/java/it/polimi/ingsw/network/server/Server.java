package it.polimi.ingsw.network.server;


import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.controller.game.GameState;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Server {

    private final GameState gameState;
    private final GameModel gameModel;
    private final Map<String, ClientHandler> clientHandlerMap;
    private final GameController controller;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(GameState gameState) {
        this.gameState = gameState;
        this.lock = new Object();
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.gameModel = gameState.getGameModel();
        this.controller = new GameController();
    }

    /**
     *
     * @param message the message to be forwarded.
     */
    public void onMessageReceived(Message message) {
        controller.onMessageReceived(message);
    }
    /**
     * Adds a client to be managed by the server instance.
     *
     * @param nickname      the nickname associated with the client.
     * @param clientHandler the ClientHandler associated with the client.
     */
    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView vv = new VirtualView(clientHandler);

    }
    /**
     * Handles the disconnection of a client.
     *
     * @param clientHandler the client disconnecting.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        String nickname = getNicknameFromClientHandler(clientHandler);


    }

    /**
     * Returns the corresponding nickname of a ClientHandler.
     *
     * @param clientHandler the client handler.
     * @return the corresponding nickname of a ClientHandler.
     */
    private String getNicknameFromClientHandler(ClientHandler clientHandler) {
        return clientHandlerMap.entrySet()
                .stream()
                .filter(entry -> clientHandler.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
