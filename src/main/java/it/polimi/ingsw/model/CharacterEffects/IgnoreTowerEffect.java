package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.player.PlayerModel;
//6
public class IgnoreTowerEffect extends Effect{
    private int costForEffect = 3;
    private GameController controller;

    public IgnoreTowerEffect(GameController controller){
        this.controller = controller;
    }
    @Override
    void enable(PlayerModel playerModel) {
        controller.setConsiderTower(false);

        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }
}
