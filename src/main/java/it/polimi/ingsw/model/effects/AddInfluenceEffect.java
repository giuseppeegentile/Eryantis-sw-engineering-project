package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//8
public class AddInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 1073341693883814645L;
    private int costForEffect = 2;
    GameController gameController;

    public AddInfluenceEffect(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        gameController.setPlayerWithEffectAdditionalInfluence(playerModel);
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
