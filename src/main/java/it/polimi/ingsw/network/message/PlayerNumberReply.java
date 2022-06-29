package it.polimi.ingsw.network.message;

/**
 * Client to server. Message sent as a response to the number of players' choice
 */
public class PlayerNumberReply extends Message {

    private static final long serialVersionUID = -4419241297635925047L;
    private final int playerNumber;

    /**
<<<<<<< HEAD
     * Message sent as a response to the number of players' choice
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param nickname current player
     * @param playerNumber number of players chosen
     */

    public PlayerNumberReply(String nickname, int playerNumber) {
        super(nickname, MessageType.PLAYERNUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    /**
     * @return the number of players
     */

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public String toString() {
        return "PlayerNumberReply{" +
                "player=" + getNickname() +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
