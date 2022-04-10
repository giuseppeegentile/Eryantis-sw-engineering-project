package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland.NONE;

public class IslandModel {
    private boolean motherNature;
    private List<ColorPawns> students;
    private ColorTower colorTower;
    private boolean isJoined;
    private PlayerModel influence;


    public IslandModel(boolean motherNature){
        this.students = null;
        this.motherNature = motherNature;
        this.isJoined = false;
    }

    //quando creo l'isola so già dove va madre natura (ricorda vincolo solo su un'isola, questo vincolo non va in questa classe ma dove creo l'array di isole, cioè Game)
    //e so anche il colore dello studente che ci piazzo su
    public IslandModel(boolean motherNature, ColorPawns initialStudent){
        this.students = new ArrayList<>();
        this.students.add(initialStudent);
        this.motherNature = motherNature;
        this.isJoined = false;
    }
    //usato principalmente nell'unione delle isole, quando devo traslocare tutti gli studenti di un'isola in un'altra
    public IslandModel(boolean motherNature, List<ColorPawns> students){
        this.students = new ArrayList<>();
        this.students.addAll(students);
        this.motherNature = motherNature;
        this.isJoined = false;
    }

    public void addStudent(ColorPawns student){
        if(this.students == null) this.students = new ArrayList<>();
        this.students.add(student);
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

    public ColorTower getTowerColor(){
        return colorTower;
    }

    public void setTowerColor(ColorTower colorTower){
        this.colorTower = colorTower;
    }

   /* private PlayerModel getInfluece(){
        return new PlayerModel("", ColorTower.GREY, 0);
    }*/

}
