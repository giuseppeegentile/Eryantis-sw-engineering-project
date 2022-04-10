package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

//4.1
public class StudentToIslandState implements PlayerState {
    private PlayerModel playerModel;

    public StudentToIslandState(PlayerModel playerModel, IslandModel islandModel){
        this.playerModel = playerModel;
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

    public void moveStudentToIsland(ColorPawns student, IslandModel islandModel){
        islandModel.addStudent(student);
        this.playerModel.removeStudentFromEntrance(student);
    }
}
