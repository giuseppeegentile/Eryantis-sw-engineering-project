package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//9
public class ExcludeColorInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 3426679041693564864L;
    private int costForEffect;
    private final GameController gameController;
    private ColorPawns colorToExclude;

    /**
     * Adds the card's effect to game controller
     */

    public ExcludeColorInfluenceEffect(GameController gameController){
        this.gameController = gameController;
        this.costForEffect = 3;
    }

    /**
     * @param colorToExclude gets the color to exclude chosen by the player
     */

    public void choose(ColorPawns colorToExclude){
        this.colorToExclude = colorToExclude;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        gameController.setIgnoreColorEffect(colorToExclude);
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
        return "EFFECT: choose a student color; this turn, during the influence calculation that color provides no influence.";
    }

    /**
     * @return the color to exclude
     */

    public ColorPawns getColorToExclude() {
        return colorToExclude;
    }

}
