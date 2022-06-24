package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//6
public class IgnoreTowerEffect implements Effect, Serializable {
    private static final long serialVersionUID = 2400877543729271601L;
    private int costForEffect;
    private final GameController controller;

    /**
     * Constructor for the character card tha has "ignore tower" as effect: costForEffect is set to 3
     * @param controller it's the game controller
     */

    public IgnoreTowerEffect(GameController controller){
        this.controller = controller;
        this.costForEffect = 3;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        controller.setConsiderTower(false);
    }

    @Override
    public void incrementCost() {
        this.costForEffect++;
        System.out.println(this.costForEffect);
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }

    @Override
    public String getDescription() {
        return "EFFECT: when counting the influence on an island (or a group of islands), the towers present are not calculated.";
    }
}

