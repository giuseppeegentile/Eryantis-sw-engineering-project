package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a socket client implementation.
 */
public class SocketClient extends Client {

    private final Socket socket;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ExecutorService readExecutionQueue;
    private final ScheduledExecutorService pinger;

    private static final int SOCKET_TIMEOUT = 10000;

    /**
     * Handles the connection from the client side
     * @param address server's address
     * @param port server's port
     * @throws IOException
     */

    public SocketClient(String address, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port), SOCKET_TIMEOUT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Asynchronously reads a message from the server via socket and notifies the ClientController.
     */
    @Override
    public void readMessage() {
        readExecutionQueue.execute(() -> {
            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) inputStream.readObject();
                    //Client.LOGGER.info("Received: " + message);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    message = new ErrorMessage(null, "Connection lost with the server.");
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
                notifyObserver(message);
            }
        });
    }

    /**
     * Sends a message to the server via socket.
     *
     * @param message the message to be sent.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            disconnect();
            notifyObserver(new ErrorMessage(null, "Could not send message."));
        }
    }

    /**
     * Disconnect the socket from the server.
     */
    @Override
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                enablePinger(false);
                socket.close();
            }
        } catch (IOException e) {
            notifyObserver(new ErrorMessage(null, "Could not disconnect."));
        }
    }

    /**
     * Enable a heartbeat (ping messages) between client and server sockets to keep the connection alive.
     *
     * @param enabled set this argument to {@code true} to enable the heartbeat.
     *                set to {@code false} to kill the heartbeat.
     */
    public void enablePinger(boolean enabled) {
        if (enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 0, 1000, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }
}
