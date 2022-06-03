package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//9
public class ExcludeColorInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 3426679041693564864L;
    private int costForEffect = 3;
    private final GameController gameController;
    private ColorPawns colorToExclude;

    public ExcludeColorInfluenceEffect(GameController gameController){
        this.gameController = gameController;
    }

    public void chose(ColorPawns colorToExclude){
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
        return "EFFECT: choose a student color; this turn, during the influence calculation that color provides no influence.";
    }

    public ColorPawns getColorToExclude() {
        return colorToExclude;
    }

}
