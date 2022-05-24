package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

public interface Effect{

    void enable(PlayerModel playerModel);


    int getCoinsForEffect();


}
