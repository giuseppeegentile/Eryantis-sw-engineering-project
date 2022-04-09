package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameMode;

import java.util.ArrayList;
import java.util.List;

public class CloudModel {
    List<ColorPawns> students;
    GameMode mode;

    private List<ColorPawns> getStudent(){
        return this.students;
    }

    public CloudModel(List<ColorPawns> initialStudents){
        this.students = initialStudents;

    }
}
