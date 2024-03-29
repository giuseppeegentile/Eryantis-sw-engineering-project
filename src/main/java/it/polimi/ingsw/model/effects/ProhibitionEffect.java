package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

/**
 * Model class of the character card with "prohibition" effect
 */

public class ProhibitionEffect implements Effect, Serializable {
    private static final long serialVersionUID = -3636736011809156600L;
    private int costForEffect;
    private int moneyOnCard = 0;
    private int chosenIndexIsland;
    private int numberProhibition = 4;

    /**
     * Initializes the parameter chosen by the player
     */

    public void choose(int chosenIndexIsland){
        this.chosenIndexIsland = chosenIndexIsland;
    }

    /**
     * Constructor for the character card tha has "prohibition" as effect: costForEffect is set to 2
     */
    public ProhibitionEffect(){
        this.costForEffect =2;
    }
    @Override
    public void enable(PlayerModel playerModel) {
        GameModel.getInstance().getIslandsModel().get(chosenIndexIsland).setHasProhibition(true);
        numberProhibition--;
    }

    @Override
    public void incrementCost() {
        this.costForEffect++;
        moneyOnCard++;
    }

    @Override
    public int getMoneyOnCard() {
        return moneyOnCard;
    }

    /**
     * Ends the effect of the card: HasProhibition is set to false
     */

    public void endEffect(){
        this.numberProhibition++;
        GameModel.getInstance().getIslandsModel().get(chosenIndexIsland).setHasProhibition(false);
    }


    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }



    @Override
    public String getDescription() {
        return "At the beginning of the game, place the 4 prohibition tiles on this card.\n" +
                "EFFECT: place a ban tile on an island of your choice. The first time mother nature ends her movement there, put the prohibition tile back on the card without calculating the influence on that island or placing towers.";

    }

    /**
     * @return the prohibition's number
     */

    public int getNumberProhibition() {
        return numberProhibition;
    }
}
