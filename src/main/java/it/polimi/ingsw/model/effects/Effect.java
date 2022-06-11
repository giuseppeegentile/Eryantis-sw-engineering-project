package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.player.PlayerModel;

public interface Effect{

    void enable(PlayerModel playerModel);

    int getCoinsForEffect();

    void incrementCost();

    String getDescription();
}