package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

public class ProhibitionEffect implements Effect, Serializable {
    private static final long serialVersionUID = -3636736011809156600L;
    private int costForEffect = 2;
    private int chosenIndexIsland;
    private int numberProhibition = 4;

    public void chose(int chosenIndexIsland){
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
        return null;
    }
}
