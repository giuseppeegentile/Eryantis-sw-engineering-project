package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IslandController {
    private IslandModel islandModel;
    //IslandView islandView;


    /**
     * Constructor for IslandController
     * @param islandModel The island to be initialized
     */
    public IslandController(IslandModel islandModel){
        this.islandModel = islandModel;
    }

    /**
     *
     * @param studentsToAdd The list of students to add to the island
     */
    public void addStudent(List<ColorPawns> studentsToAdd){
        islandModel.getStudents().addAll(studentsToAdd);
    }

    /**
     * Sets the tower color of the island when this is conquered by a player
     * @param colorTower The tower color of the conquering player
     */
    public void setTowerColor(ColorTower colorTower){
        islandModel.setTowerColor(colorTower);
    }



}
