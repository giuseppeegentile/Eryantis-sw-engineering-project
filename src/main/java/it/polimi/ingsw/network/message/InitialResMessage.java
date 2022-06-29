package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;

import java.util.List;

/**
 * Server to client. Message sent as a response to the initial configuration request
 */
public class InitialResMessage extends Message{

    private static final long serialVersionUID = 4898299466144192135L;

    private final List<ColorTower> availableTowers;
    private GameMode gameMode;

    /**
<<<<<<< HEAD
     * Message sent as a response to the initial configuration request
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param nickname current player
     * @param availableTowers list of tower's colors available
     * @param gameMode game mode chosen
     */

    public InitialResMessage(String nickname, List<ColorTower> availableTowers, GameMode gameMode) {
        super(nickname, MessageType.INIT);
        this.availableTowers = availableTowers;
        this.gameMode = gameMode;
    }

    /**
     * @return the list of the available tower colors
     */

    public List<ColorTower> getAvailableTowers() {
        return availableTowers;
    }

    /**
     * @return the game mode chosen
     */

    public GameMode getGameMode(){
        return gameMode;
    }
    @Override
    public String toString() {
        return "InitialResMessage{" +
                "player=" + getNickname() +
                ", availableTowers=" + availableTowers +
                '}';
    }

}
