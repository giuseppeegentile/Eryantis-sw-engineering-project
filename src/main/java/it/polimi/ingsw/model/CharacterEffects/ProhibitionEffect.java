package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

public class ProhibitionEffect implements Effect {
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
}
