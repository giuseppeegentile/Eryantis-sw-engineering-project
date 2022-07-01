package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

/**
 * Game's server application
 */

public class ServerApp {

    public static void main(String[] args) {
        int serverPort = 16847;


        for (int i = 0; i < args.length; i++) {
            if ((args[i].equals("--port"))) {
                try {
                    serverPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    Server.LOGGER.warning("Invalid port specified. Using default port.");
                }
            }else
                System.out.println("missing");
        }

        GameController gameController = new GameController();
        Server server = new Server(gameController);

        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}
