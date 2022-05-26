package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

public class CharacterCardModel implements Serializable {

    private static final long serialVersionUID = -5946216226246714494L;

    private int moneyOnCard;
    private Effect effect;
    private int characterId;

    private PlayerModel owner;
    public CharacterCardModel(int moneyOnCard, Effect effect, int characterId){
        this.moneyOnCard = moneyOnCard;
        this.effect = effect;
        this.characterId = characterId;
    }

    private void setCharacterId(int characterId){
        this.characterId = characterId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public int getMoneyOnCard() {
        return moneyOnCard;
    }

    public void setMoneyOnCard(int moneyOnCard) {
        this.moneyOnCard = moneyOnCard;
    }


    public void incrementMoneyCost(){
        this.moneyOnCard++;
    }

    public boolean enoughCoins(){
        return effect.getCoinsForEffect() >= moneyOnCard;
    }

    public PlayerModel getOwner() {
        return owner;
    }

    public void setOwner(PlayerModel owner) {
        this.owner = owner;
    }

}
