package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;
//9
public class ExcludeColorInfluenceEffect implements Effect{
    private int costForEffect = 3;
    private final GameController gameController;
    private ColorPawns colorToExclude;

    public ExcludeColorInfluenceEffect(GameController gameController){
        this.gameController = gameController;
    }

    public void setColorToExclude(ColorPawns colorToExclude){
        this.colorToExclude = colorToExclude;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        gameController.setIgnoreColorEffect(colorToExclude);
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
