package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

/**
 *  Server to client. Message sent when a player wins the game
 */

public class WinMessage  extends Message {
    private static final long serialVersionUID = 4516402749630283459L;

    public PlayerModel getWinner() {
        return winner;
    }

    private final PlayerModel winner;

    /**
     * Default constructor.
     * Parameters are set by the constructor.
     * @param winnerNickname nickname of the winner
     */

    public WinMessage(PlayerModel winnerNickname) {
        super(GameModel.SERVER_NICKNAME, MessageType.WIN);
        this.winner = winnerNickname;
    }

    @Override
    public String toString() {
        return "WinMessage{" +
                "player=" + getNickname() +
                ", winnerNickname=" + winner.getNickname() +
                '}';
    }
}