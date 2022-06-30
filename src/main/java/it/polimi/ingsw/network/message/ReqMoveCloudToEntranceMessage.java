package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.game.CloudModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Server to client. Message sent to move students from clouds to the entrance
 */

public class ReqMoveCloudToEntranceMessage extends Message{

    private static final long serialVersionUID = -175250577233070966L;
    private final List<CloudModel> clouds;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param clouds list of clouds
     */

    public ReqMoveCloudToEntranceMessage(String nickname, List<CloudModel> clouds) {
        super(nickname, MessageType.MOVE_CLOUD_TO_ENTRANCE);
        this.clouds = new ArrayList<>(clouds);
    }

    /**
     * @return the list of the clouds
     */

    public List<CloudModel> getClouds(){
        return clouds;
    }

    @Override
    public String toString() {
        return "ReqMoveCloudToEntranceMessage{" +
                "player=" + getNickname() +
                ", clouds=" + clouds +
                '}';
    }
}
