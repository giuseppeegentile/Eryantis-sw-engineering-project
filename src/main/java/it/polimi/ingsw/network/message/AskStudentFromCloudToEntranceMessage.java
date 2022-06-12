package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.game.CloudModel;
import java.util.ArrayList;
import java.util.List;

public class AskStudentFromCloudToEntranceMessage extends Message{

    private static final long serialVersionUID = -175250577233070966L;
    private final List<CloudModel> clouds;

    /**
     * Message shown when asked to move a student from a cloud to a player's board's entrance
     * @param nickname nickname of the player that makes a move
     * @param clouds list of clouds
     */

    public AskStudentFromCloudToEntranceMessage(String nickname, List<CloudModel> clouds) {
        super(nickname, MessageType.MOVE);
        this.clouds = new ArrayList<>(clouds);
    }

    public List<CloudModel> getClouds(){
        return clouds;
    }

    @Override
    public String toString() {
        return "AskStudentFromCloudToEntranceMessage{" +
                "nickname=" + getNickname() +
                ", clouds=" + clouds +
                '}';
    }
}
