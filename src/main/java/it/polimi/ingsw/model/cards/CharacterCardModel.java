package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

public class CharacterCardModel implements Serializable {

    private static final long serialVersionUID = -5946216226246714494L;

    private int moneyOnCard;
    private Effect effect;
    private final int characterId;
    private PlayerModel owner;

    /**
     * Constructor for character card in the model
     * @param moneyOnCard The number of money placed on the character card
     * @param effect The effect of the card
     * @param characterId The identifier of the character card
     */
    public CharacterCardModel(int moneyOnCard, Effect effect, int characterId){
        this.moneyOnCard = 0;
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
     * @return The number of the money placed on the character card
     */
    public int getMoneyOnCard() {
        return moneyOnCard;
    }

    /**
     * It increments by 1 the cost of the activation of the character card effect
     */
    public void incrementMoneyCost(){
        this.moneyOnCard++;
    }

    /**
     *
     * @return True if the money placed on card are enough to activate the card
     */
    public boolean enoughCoins(int money){
        return money >= effect.getCoinsForEffect();
    }

    /**
     *
     * @return The player who owns the character card
     */
    public PlayerModel getOwner() {
        return owner;
    }

    /**
     *
     * @param owner The player who owns the character card
     */
    public void setOwner(PlayerModel owner) {
        this.owner = owner;
    }
}
