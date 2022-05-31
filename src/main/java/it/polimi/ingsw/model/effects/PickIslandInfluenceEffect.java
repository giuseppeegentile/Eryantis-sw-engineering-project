package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;

//3
public class PickIslandInfluenceEffect implements Effect, Serializable {
    private static final long serialVersionUID = 3251436688028470073L;
    int costForEffect = 3;
    private int indexIslandEffect;
    private final GameController gameController;

    public PickIslandInfluenceEffect(GameController controller){
        this.gameController = controller;
    }

    public void chose(int indexIslandEffect){
        this.indexIslandEffect = indexIslandEffect;
    }

    @Override
    public void enable(PlayerModel playerModel) {
        IslandModel islandChosen = GameModel.getInstance().getIslandsModel().get(indexIslandEffect);

        gameController.computeIslandsChanges(playerModel, islandChosen);
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

    public int getIndexIslandEffect() {
        return indexIslandEffect;
    }

    public void setIndexIslandEffect(int indexIslandEffect) {
        this.indexIslandEffect = indexIslandEffect;
    }
}