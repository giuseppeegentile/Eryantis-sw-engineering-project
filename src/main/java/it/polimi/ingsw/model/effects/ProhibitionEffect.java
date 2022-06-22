package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

public class ProhibitionEffect implements Effect, Serializable {
    private static final long serialVersionUID = -3636736011809156600L;
    private int costForEffect;
    private int chosenIndexIsland;
    private int numberProhibition = 4;

    /**
     * Sets rhw index of the chosen island
     */

    public void choose(int chosenIndexIsland){
        this.chosenIndexIsland = chosenIndexIsland;
    }

    /**
     * Sets the card's cost in number of coins
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
        System.out.println(this.costForEffect);
    }

    /**
     * Ends the card's effect
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
