package it.polimi.ingsw.network.message;

public class MovedBanCardMessage extends Message {

    private static final long serialVersionUID = -7727095324726678974L;
    private final int indexIsland;

    public MovedBanCardMessage(String active, int indexIsland) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    public int getIndexIsland() {
        return indexIsland;
    }

    @Override
    public String toString() {
        return "MovedBanCardMessage{" +
                "player=" + getNickname() +
                ", indexIsland=" + indexIsland +
                '}';
    }
}
