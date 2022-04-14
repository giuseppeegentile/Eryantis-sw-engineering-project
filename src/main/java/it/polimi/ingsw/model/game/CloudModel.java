package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;

import java.util.ArrayList;
import java.util.List;

public class CloudModel {
    private List<ColorPawns> students;
    private final int size; //number of student in the cloud

    public List<ColorPawns> getStudents(){
        return this.students;
    }

    public void cleanStudent(){
        this.students = new ArrayList<>(size);
    }

    public CloudModel(int size){
        this.size = size;
    }

    public void setStudents(List<ColorPawns> students) {
        this.students = students;
    }
}
