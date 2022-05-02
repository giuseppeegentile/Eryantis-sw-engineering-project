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
