package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.Effect;

import java.io.Serializable;

public class CharacterCardModel implements Serializable {

    private static final long serialVersionUID = -5946216226246714494L;

    private Effect effect;
    private final int characterId;

    /**
     * Constructor for character card in the model
     * @param effect The effect of the card
     * @param characterId The identifier of the character card
     */
    public CharacterCardModel(Effect effect, int characterId){
        this.effect = effect;
        this.characterId = characterId;
    }

    /**
     *
     * @return The identifier of the character card
     */
    public int getCharacterId() {
        return characterId;
    }

    /**
     *
     * @return The effect of the character card
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     *
     * @param effect The effect to be assigned to the character card
     */
    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    /**
     *
     * @return True if the money placed on card are enough to activate the card
     */
    public boolean enoughCoins(int money){
        return money >= effect.getCoinsForEffect();
    }
}
