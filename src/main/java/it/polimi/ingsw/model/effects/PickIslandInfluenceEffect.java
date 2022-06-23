package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//3
public class PickIslandInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 3251436688028470073L;
    private int costForEffect;
    private int indexIslandEffect;
    private final GameController gameController;

    /**
     * Constructor for the character card tha has "pick island influence" as effect: costForEffect is set to 3
     * @param controller it's the game controller
     */

    public PickIslandInfluenceEffect(GameController controller){
        this.gameController = controller;
        this.costForEffect = 3;
    }

    /**
     * Constructor made to initialize the parameters chosen by the player
     * @param indexIslandEffect the index where to perform the influence effect
     */
    public void choose(int indexIslandEffect){
        this.indexIslandEffect = indexIslandEffect;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        IslandModel islandChosen = GameModel.getInstance().getIslandsModel().get(indexIslandEffect);

        gameController.computeIslandsChanges(playerModel, islandChosen);
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
        return "EFFECT: choose an island and calculate the majority as if mother nature ended her movement there. In this turn, mother nature will move as usual and on the island where her movement ends, the majority will normally be calculated.";
    }

    /**
     * @return the index of the island
     */
    public int getIndexIslandEffect() {
        return indexIslandEffect;
    }

    /**
     * Constructor that sets the index of the island in which activate the effect
     * @param indexIslandEffect the index of the island
     */
    public void setIndexIslandEffect(int indexIslandEffect) {
        this.indexIslandEffect = indexIslandEffect;
    }
}
