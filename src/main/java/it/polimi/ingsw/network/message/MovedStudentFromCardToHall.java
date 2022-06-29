package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

<<<<<<< HEAD
=======
/**
 * Client to server. Message sent to move students from a character card to the player's hall
 */

>>>>>>> main
public class MovedStudentFromCardToHall extends Message {
    private static final long serialVersionUID = -6569522000210948266L;
    private final ColorPawns pickedStudent;

    /**
<<<<<<< HEAD
     * Message sent to move students from a character card to the player's hall
=======
     * Default constructor
>>>>>>> main
     * Parameters are set by the constructor
     * @param nickname current player
     * @param pickedStudent student chosen
     */

    public MovedStudentFromCardToHall(String nickname, ColorPawns pickedStudent) {
        super(nickname, MessageType.EFFECT_CARD_PLAYED);
        this.pickedStudent = pickedStudent;
    }

    /**
     * @return the student chosen
     */

    public ColorPawns getPickedStudent() {
        return pickedStudent;
    }

    @Override
    public String toString() {
        return "MovedStudentFromCardToHall{" +
                "player=" + getNickname() +
                ", pickedStudent=" + pickedStudent +
                '}';
    }
}

