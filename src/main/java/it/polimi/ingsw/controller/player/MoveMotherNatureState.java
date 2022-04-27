package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;

import java.util.List;

//5
public class MoveMotherNatureState implements PlayerState {
    private final PlayerModel playerModel;
    private final GameModel gameModel;

    /**
     * Constructor for MoveMotherNatureState: sets the player state to MOVE_MOTHER_NATURE
     * @param playerModel The player who can move mother nature
     */
    public MoveMotherNatureState(PlayerModel playerModel, GameModel gameModel){
        this.playerModel = playerModel;
        this.gameModel = gameModel;
        this.playerModel.setState(StatePlayer.MOVE_MOTHER_NATURE);
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }


    //restituisce la lista di Isole con madre natura aggiornata
    /**
     * Updates the list of island with the new position of mother nature
     * @param movementMotherNature The number of movements mother nature is allowed to do
     */
    public void moveMotherNature(byte movementMotherNature){
        int indexOldMotherNature = 0;
        List<IslandModel> islandsModels = this.gameModel.getIslandsModel();
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;

        int newIndex = (indexOldMotherNature + movementMotherNature) % 12;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        this.gameModel.setIslands(islandsModels);
    }

}
