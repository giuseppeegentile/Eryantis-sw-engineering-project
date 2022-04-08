package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.ColorDirectionAdjacentIsland.NONE;

public class IslandModel {
    private boolean motherNature;
    private List<ColorPawns> students;
    private ColorTower colorTower;
    private boolean isJoined;
    private int numJoined;
    private PlayerModel influence;

    //quando creo l'isola so già dove va madre natura (ricorda vincolo solo su un'isola, questo vincolo non va in questa classe ma dove creo l'array di isole, cioè Game)
    //e so anche il colore dello studente che ci piazzo su
    public IslandModel(boolean motherNature, ColorPawns initialStudent){
        this.students = new ArrayList<ColorPawns>();
        this.students.add(initialStudent);
        this.motherNature = motherNature;
        this.isJoined = false;

    }

    public void setJoined(){
        this.isJoined = true;
    }

    public boolean getMotherNature(){
        return motherNature;
    }

    public List<ColorPawns> getStudents(){
        return students;
    }

    protected ColorTower getTowerColor(){
        return colorTower;
    }

    public void setTowerColor(ColorTower colorTower){
        this.colorTower = colorTower;
    }

    private ColorDirectionAdjacentIsland isAdjacentSameColor(){
        return NONE; //da sistemare, temporaneo
    }

    private PlayerModel getInfluece(){
        return new PlayerModel("", ColorTower.GREY, 0);
    }

}
