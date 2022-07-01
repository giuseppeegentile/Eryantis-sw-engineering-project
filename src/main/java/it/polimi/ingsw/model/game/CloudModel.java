package it.polimi.ingsw.model.game;


import it.polimi.ingsw.model.colors.ColorPawns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class of the clouds
 */

public class CloudModel implements Serializable {
    private static final long serialVersionUID = -8954335784271144048L;
    private List<ColorPawns> students;
    private final int size; //number of student in the cloud

    /**
     *
     * @return The list of students on the cloud
     */
    public List<ColorPawns> getStudents(){
        return this.students;
    }

    /**
     * Removes all the students from the cloud
     */
    public void cleanStudent(){
        this.students = new ArrayList<>(size);
    }

    /**
     *
     * @param size The number of students on the cloud. It depends on the game mode
     */
    public CloudModel(int size){
        this.size = size;
    }

    /**
     *
     * @param students The list of the students to add to the cloud
     */
    public void setStudents(List<ColorPawns> students) {
        this.students = students;
    }
}

