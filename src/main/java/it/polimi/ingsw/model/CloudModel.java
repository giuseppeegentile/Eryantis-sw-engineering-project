package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CloudModel {
    List<ColorPawns> students;
    GameMode mode;

    private List<ColorPawns> getStudent(){
        return students;
    }

    public CloudModel(List<ColorPawns> initialStudents){
        students = new ArrayList<ColorPawns>(initialStudents.size());
        this.students = initialStudents;

    }
}
