package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//6
public class IgnoreTowerEffect implements Effect, Serializable {
    private static final long serialVersionUID = 2400877543729271601L;
    private int costForEffect = 3;
    private final GameController controller;

    public IgnoreTowerEffect(GameController controller){
        this.controller = controller;
    }
    @Override
    public void enable(PlayerModel playerModel) {
        controller.setConsiderTower(false);

        costForEffect++;
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
