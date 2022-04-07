package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.IslandModel;
import it.polimi.ingsw.model.ColorTower;

import java.util.List;

public class IslandController {
    IslandModel islandModel;
    //IslandView islandView;


    IslandController(IslandModel islandModel){
        this.islandModel = islandModel;
    }

    public void addStudent(List<ColorPawns> studentsToAdd){
        islandModel.getStudents().addAll(studentsToAdd);
    }

    public void setTowerColor(ColorTower colorTower){
        islandModel.setTowerColor(colorTower);
    }
}
