package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

/**
 *  Client to server. Message sent when moving a student from a character card to an island
 */

public class MovedFromCardToIsland extends Message{
    private static final long serialVersionUID = 5594665525893264015L;
    private final int islandIndex;
    private final ColorPawns studentToMove;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param islandIndex index of the island chosen
     * @param studentToMove student chosen
     */

    public MovedFromCardToIsland(String nickname, int islandIndex, ColorPawns studentToMove) {
        super(nickname, MessageType.EFFECT_CARD_PLAYED);
        this.islandIndex = islandIndex;
        this.studentToMove = studentToMove;
    }

    /**
     * @return index of the island chosen
     */

    public int getIslandIndex() {
        return islandIndex;
    }

    /**
     * @return student chosen
     */

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
