package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.model.player.PlayerModel;
//4
public class ExtraMovementMotherEffect extends Effect {
    private int costForEffect =1;


    @Override
    void enable(PlayerModel playerModel) {
        byte oldMoveAllowed = playerModel.getMovementMotherNatureCurrentActionPhase();
        playerModel.setMovementMotherNatureCurrentActionPhase((byte) (oldMoveAllowed+2));
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }
}
