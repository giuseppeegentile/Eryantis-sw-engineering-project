package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

import java.util.List;

//5
public class MoveMotherNatureState implements PlayerState {
    private PlayerModel playerModel;
    private GameModel gameModel;

    public MoveMotherNatureState(PlayerModel playerModel, GameModel gameModel){
        this.playerModel = playerModel;
        this.gameModel = gameModel;
        this.playerModel.setState(StatePlayer.MOVE_MOTHER_NATURE);
    }

    @Override
    public void addCoins() {

    }

    @Override
    public void setCoins() {

    }

    @Override
    public void decrementCoins(int coinsUsed) {

    }

    //restituisce la lista di Isole con madre natura aggiornata
    public void moveMotherNature(byte movementMotherNature){
        int indexOldMotherNature = 0;
        List<IslandModel> islandsModels = this.gameModel.getIslandsModel();
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;
        int newIndex = indexOldMotherNature + movementMotherNature;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        this.gameModel.setIslands(islandsModels);
    }

}
