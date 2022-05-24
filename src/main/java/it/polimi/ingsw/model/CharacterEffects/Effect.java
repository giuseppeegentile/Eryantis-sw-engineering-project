package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

public abstract class Effect{

    abstract void enable(PlayerModel playerModel);


    public abstract int getCoinsForEffect();


}
