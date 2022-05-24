package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.player.PlayerModel;

//8
public class AddInfluenceEffect extends Effect{
    private int costForEffect = 2;
    GameController gameController;

    public AddInfluenceEffect(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    void enable(PlayerModel playerModel) {
        gameController.setPlayerWithEffectAdditionalInfluence(playerModel);
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }
}
