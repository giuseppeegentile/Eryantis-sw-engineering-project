package it.polimi.ingsw.network.message;

/**
 * Sent from client to server, containing which cloud he chose to move to entrance.
 */
public class AddStudentFromCloudToEntranceMessage extends Message {
    private static final long serialVersionUID = 6859183237575060635L;

    private final int cloudIndex;

    /**
<<<<<<< HEAD
     * Message sent when a student is added from a cloud to a player's board's entrance
     * Parameters are set by the constructor
     * @param nickname nickname of the player who makes the move
     * @param cloudIndex index of the cloud chosen
=======
     * Message sent when a student is added from a cloud to a player's board's entrance.
     * @param nickname nickname of the player who makes the move.
     * @param cloudIndex index of the cloud chosen.
>>>>>>> main
     */

    public AddStudentFromCloudToEntranceMessage(String nickname, int cloudIndex) {
        super(nickname, MessageType.MOVED_CLOUD_TO_ENTRANCE);
        this.cloudIndex = cloudIndex;
    }

    /**
<<<<<<< HEAD
     * @return the cloud's index
=======
     * @return the cloud's chosen index.
>>>>>>> main
     */

    public int getCloudIndex(){
        return cloudIndex;
    }

    @Override
    public String toString() {
        return "AddStudentFromCloudToEntranceMessage{" +
                "nickname=" + getNickname() +
                ", cloudIndex=" + cloudIndex +
                '}';
    }
}
