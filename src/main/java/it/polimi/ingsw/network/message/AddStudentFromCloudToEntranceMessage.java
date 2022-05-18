package it.polimi.ingsw.network.message;

public class AddStudentFromCloudToEntranceMessage extends Message {
    private static final long serialVersionUID = 6859183237575060635L;

    private final int cloudIndex;

    public AddStudentFromCloudToEntranceMessage(String nickname, int cloudIndex) {
        super(nickname, MessageType.MOVE);
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex(){
        return cloudIndex;
    }

    @Override
    public String toString() {
        return "AddStudentFromCloudToWaitingMessage{" +
                "nickname=" + getNickname() +
                ", cloudIndex=" + cloudIndex +
                '}';
    }
}
