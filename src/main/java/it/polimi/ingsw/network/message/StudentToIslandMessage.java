package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.List;

public class StudentToIslandMessage extends Message{
    private static final long serialVersionUID = 1729951793698626264L;

    private final List<ColorPawns> students;

    /**
     * Message shown when a player moves a student to an island
     * @param nickname current player
     * @param students students to be moved
     */

    public StudentToIslandMessage(String nickname, List<ColorPawns> students) {
        super(nickname, MessageType.MOVE);
        this.students = students;
    }

    public List<ColorPawns> getStudents() {
        return students;
    }


    @Override
    public String toString() {
        return "StudentToIslandMessage{" +
                "player=" + getNickname() +
                ", students=" + students +
                '}';
    }

}
