package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.game.CloudModel;
import java.util.ArrayList;
import java.util.List;

public class AskStudentFromCloudToEntranceMessage extends Message{

    private static final long serialVersionUID = -175250577233070966L;
    private final List<CloudModel> clouds;

    public AskStudentFromCloudToEntranceMessage(String nickname, List<CloudModel> clouds) {
        super(nickname, MessageType.MOVE);
        this.clouds = new ArrayList<>(clouds);
    }

    public List<CloudModel> getClouds(){
        return clouds;
    }

    @Override
    public String toString() {
        return "AddStudentFromCloudToWaitingMessage{" +
                "nickname=" + getNickname() +
                ", clouds=" + clouds +
                '}';
    }
}
