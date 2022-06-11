package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

public class MovedFromCardToIsland extends Message{
    private static final long serialVersionUID = 5594665525893264015L;
    private final int islandIndex;
    private final ColorPawns studentToMove;

    public MovedFromCardToIsland(String nickname, int islandIndex, ColorPawns studentToMove) {
        super(nickname, MessageType.EFFECT_CARD_PLAYED);
        this.islandIndex = islandIndex;
        this.studentToMove = studentToMove;
    }

    public int getIslandIndex() {
        return islandIndex;
    }

    public ColorPawns getStudentToMove() {
        return studentToMove;
    }

    @Override
    public String toString() {
        return "MovedFromCardToIsland{" +
                "player=" + getNickname() +
                ", islandIndex=" + islandIndex +
                ", studentToMove=" + studentToMove +
                '}';
    }
}
