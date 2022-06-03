package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//4
public class ExtraMovementMotherEffect implements Effect, Serializable {
    private static final long serialVersionUID = -5520393474357646858L;
    private int costForEffect =1;


    @Override
    public void enable(PlayerModel playerModel) {
        byte oldMoveAllowed = playerModel.getMovementMotherNatureCurrentActionPhase();
        playerModel.setMovementMotherNatureCurrentActionPhase((byte) (oldMoveAllowed+2));
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return "EFFECT: you can move mother nature up to 2 more islands than indicated on the assistant card you played";
    }
}
