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


    public IslandController(IslandModel islandModel){
        this.islandModel = islandModel;
    }

    public void addStudent(List<ColorPawns> studentsToAdd){
        islandModel.getStudents().addAll(studentsToAdd);
    }

    public void setTowerColor(ColorTower colorTower){
        islandModel.setTowerColor(colorTower);
    }



}
