package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.player.PlayerModel;

/**
 * Effects interface
 */

public interface Effect{

    void enable(PlayerModel playerModel);

    int getMoneyOnCard();

    int getCoinsForEffect();

    void incrementCost();

    String getDescription();
}