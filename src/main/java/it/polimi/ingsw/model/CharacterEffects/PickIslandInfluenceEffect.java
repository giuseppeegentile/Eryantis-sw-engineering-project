package it.polimi.ingsw.model.CharacterEffects;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
//3
public class PickIslandInfluenceEffect extends Effect{
    int costForEffect = 3;
    private int indexIslandEffect;
    private final GameController gameController;

    public PickIslandInfluenceEffect(int indexIslandEffect, GameController controller){
        this.indexIslandEffect = indexIslandEffect;
        this.gameController = controller;
    }

    @Override
    void enable(PlayerModel playerModel) {
        IslandModel islandChosen = GameModel.getInstance().getIslandsModel().get(indexIslandEffect);

        gameController.computeIslandsChanges(playerModel, islandChosen);
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return costForEffect;
    }

    public int getIndexIslandEffect() {
        return indexIslandEffect;
    }

    public void setIndexIslandEffect(int indexIslandEffect) {
        this.indexIslandEffect = indexIslandEffect;
    }
}
