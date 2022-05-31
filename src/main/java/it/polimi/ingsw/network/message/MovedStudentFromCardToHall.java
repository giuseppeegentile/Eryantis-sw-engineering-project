package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

public class MovedStudentFromCardToHall extends Message {
    private static final long serialVersionUID = -6569522000210948266L;
    private final ColorPawns pickedStudent;
    public MovedStudentFromCardToHall(String nickname, ColorPawns pickedStudent) {
        super(nickname, MessageType.CHARACTER_CARD_PLAYED);
        this.pickedStudent = pickedStudent;
    }

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
