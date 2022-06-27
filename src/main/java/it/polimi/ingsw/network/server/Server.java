package it.polimi.ingsw.network.server;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Server {

    private final Map<String, ClientHandler> clientHandlerMap;
    private final GameController gameController;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(GameController gameController) {
        this.lock = new Object();
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.gameController = gameController;
    }

    /**
     *
     * @param message the message to be forwarded.
     */
    public void onMessageReceived(Message message) {
        gameController.onMessageReceived(message);
    }
    /**
     * Adds a client to be managed by the server instance.
     *
     * @param nickname      the nickname associated with the client.
     * @param clientHandler the ClientHandler associated with the client.
     */
    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView vv = new VirtualView(clientHandler);

        if (gameController.checkLoginNickname(nickname, vv)) {
            clientHandlerMap.put(nickname, clientHandler);
            gameController.handleLogin(nickname, vv);
        }

     /*
        if (!gameController.isGameStarted()) {
            if (gameController.checkLoginNickname(nickname, vv)) {
                clientHandlerMap.put(nickname, clientHandler);
                gameController.handleLogin(nickname, vv);
            }
        } else {
            vv.showLoginResult(true, false, null);
            clientHandler.disconnect();
        }*/
    }
    /**
     * Handles the disconnection of a client.
     *
     * @param clientHandler the client disconnecting.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        synchronized (lock) {
            String nickname = getNicknameFromClientHandler(clientHandler);

            if (nickname != null) {

                boolean gameStarted = gameController.isGameStarted();
                removeClient(nickname);

                if(!gameController.getPlayerActive().getNickname().equals(nickname)) {
                    return;
                }


                //se il gioco è iniziato resetto il gioco, altrimenti aspetto nuovi giocatori che si connettano
                if (gameStarted) {
                    gameController.broadcastDisconnectionMessage(nickname, " disconnected from the server. GAME ENDED.");

                    //da fare un metodo del genere:
                    //GameModel.getInstance().resetGame();
                    clientHandlerMap.clear();
                }
            }
        }
    }

    /**
     * Removes a client given his nickname.
     *
     * @param nickname      the VirtualView to be removed.
     */
    private void removeClient(String nickname) {
        clientHandlerMap.remove(nickname);
        gameController.removeVirtualView(nickname);
        LOGGER.info(() -> "Removed " + nickname + " from the client list.");
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
