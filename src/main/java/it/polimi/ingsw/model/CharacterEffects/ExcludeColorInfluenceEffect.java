package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
//9
public class ExcludeColorInfluenceEffect extends Effect{
    private int costForEffect = 3;
    private final GameController gameController;
    private final ColorPawns colorToExclude;

    public ExcludeColorInfluenceEffect(GameController gameController, ColorPawns colorToExclude){
        this.gameController = gameController;
        this.colorToExclude = colorToExclude;
    }

    @Override
    void enable(PlayerModel playerModel) {
        gameController.setIgnoreColorEffect(colorToExclude);
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }
}
