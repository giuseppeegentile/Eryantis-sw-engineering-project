package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CharacterEffects.Effect;

public class WithInitialProhibitionCard extends CharacterCardModel{
    private static final long serialVersionUID = -5927680623175088106L;

    private int numberProhibitionCards;
    private boolean onIsland;

    public WithInitialProhibitionCard(int moneyCost, Effect effect, String characterId, int numberProhibitionCards) {
        super(moneyCost, effect, characterId);
        this.numberProhibitionCards = numberProhibitionCards;
        this.onIsland = false;
    }


    public int getNumberProhibitionCards() {
        return numberProhibitionCards;
    }

    public void setNumberProhibitionCards(int numberProhibitionCards) {
        this.numberProhibitionCards = numberProhibitionCards;
    }

    public boolean isOnIsland(){
        return this.onIsland;
    }

    public void setOnIsland(){
        this.onIsland = true;
    }
}
