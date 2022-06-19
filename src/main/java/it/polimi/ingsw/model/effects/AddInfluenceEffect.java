package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//8
public class AddInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 1073341693883814645L;
    private int costForEffect;
    GameController gameController;

    public AddInfluenceEffect(GameController gameController){
        this.gameController = gameController;
        this.costForEffect = 2;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        gameController.setPlayerWithEffectAdditionalInfluence(playerModel);
    }
    @Override
    public void incrementCost() {
        this.costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return "EFFECT: this turn, during the influence calculation you have 2 additional influence points.";
    }
}
