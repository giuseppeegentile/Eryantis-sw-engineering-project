package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class InitialConfigurationResponseMessage extends Message {
    private static final long serialVersionUID = 253192915029656524L;
    private final PlayerModel player;
    private final int towerNumber;
    private final List<AssistantCardModel> playerDeck;
    private final ColorTower colorTower;

    /**
     * Message shown as a response to the initial configuration
     * @param player current player
     * @param playerDeck current player's deck
     * @param colorTower current player's tower's color
     * @param towerNumber current player's tower's number
     * @param messageType type of the message
     */

    public InitialConfigurationResponseMessage(PlayerModel player, List<AssistantCardModel> playerDeck, ColorTower colorTower, int towerNumber, MessageType messageType){
        super(player.getNickname(), messageType);
        this.player = player;
        this.towerNumber = towerNumber;
        this.playerDeck = playerDeck;
        this.colorTower = colorTower;
    }

    @Override
    public String toString() {
        return "InitialConfigurationResponseMessage{" +
                "player=" + player.getNickname() +
                ", towerNumber=" + towerNumber +
                ", playerDeck=" + playerDeck +
                ", colorTower=" + colorTower +
                '}';
    }
}
