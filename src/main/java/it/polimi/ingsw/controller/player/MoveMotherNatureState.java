package it.polimi.ingsw.controller.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MoveMotherNatureMessage;

import java.util.List;

//5
public class MoveMotherNatureState implements PlayerState {
    private final PlayerModel playerModel;

    /**
     * Constructor for MoveMotherNatureState: sets the player state to MOVE_MOTHER_NATURE
     * @param playerModel The player who can move mother nature
     */
    public MoveMotherNatureState(PlayerModel playerModel){
        this.playerModel = playerModel;
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


    //modifica la lista di Isole con madre natura aggiornata
    /**
     * Updates the list of island with the new position of mother nature
     * @param movementMotherNature The number of movements mother nature is allowed to do
     */
    public void moveMotherNature(byte movementMotherNature){
        int indexOldMotherNature = 0;
        List<IslandModel> islandsModels = GameModel.getInstance().getIslandsModel();
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;

        int newIndex = (indexOldMotherNature + movementMotherNature) % 11;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        GameModel.getInstance().setIslands(islandsModels);
    }

}
