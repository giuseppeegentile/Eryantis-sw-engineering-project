package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

public class ProhibitionEffect implements Effect, Serializable {
    private static final long serialVersionUID = -3636736011809156600L;
    private int costForEffect = 2;
    private int chosenIndexIsland;
    private int numberProhibition = 4;

    public void choose(int chosenIndexIsland){
        this.chosenIndexIsland = chosenIndexIsland;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        GameModel.getInstance().getIslandsModel().get(chosenIndexIsland).setHasProhibition(true);

        numberProhibition--;
        costForEffect++;
    }

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
                "EFFECT: place a ban tile on an island of your choice. the first time mother nature ends her movement there, put the prohibition tile back on the card without calculating the influence on that island or placing towers.";
    }

    public int getNumberProhibition() {
        return numberProhibition;
    }
}
