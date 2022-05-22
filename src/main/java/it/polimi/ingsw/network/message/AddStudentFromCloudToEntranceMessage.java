package it.polimi.ingsw.network.message;

public class AddStudentFromCloudToEntranceMessage extends Message {
    private static final long serialVersionUID = 6859183237575060635L;

    private final int cloudIndex;

    /**
     * Message shown when a student is added from a cloud to a player's board's entrance
     * @param nickname nickname of the player who makes the move
     * @param cloudIndex index of the cloud chosen
     */

    public AddStudentFromCloudToEntranceMessage(String nickname, int cloudIndex) {
        super(nickname, MessageType.MOVE);
        this.cloudIndex = cloudIndex;
    }

    /**
     * Gets the index of the cloud from which a student is taken
     * @return the cloud's index
     */

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
