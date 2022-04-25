package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.player.StatePlayer;

import java.util.List;

//5
public class MoveMotherNatureState implements PlayerState {
    public PlayerModel playerModel;

    /**
     * Constructor for MoveMotherNatureState: sets the player state to MOVE_MOTHER_NATURE
     * @param playerModel The player who can move mother nature
     */
    public MoveMotherNatureState(PlayerModel playerModel){
        this.playerModel = playerModel;
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

    /**
     * Updates the list of island with the new position of mother nature
     * @param islandsModels The list of the island
     * @param movementMotherNature The number of movements mother nature is allowed to do
     * @return The updated list of islands
     */
    //restituisce la lista di Isole con madre natura aggiornata
    public List<IslandModel> moveMotherNature(List<IslandModel> islandsModels, byte movementMotherNature){
        int indexOldMotherNature = 0;
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;
        int newIndex = indexOldMotherNature + movementMotherNature;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        return islandsModels;
    }

}
