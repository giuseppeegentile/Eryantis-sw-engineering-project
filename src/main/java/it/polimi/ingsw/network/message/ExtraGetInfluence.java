package it.polimi.ingsw.network.message;

public class ExtraGetInfluence extends Message {

    private static final long serialVersionUID = 7916652247877017654L;
    private final int indexIsland;

    public ExtraGetInfluence(String active, int indexIsland) {
        super(active, MessageType.CHARACTER_CARD_PLAYED);
        this.indexIsland = indexIsland;
    }

    public int getIndexIsland() {
        return indexIsland;
    }

    @Override
    public String toString() {
        return "ExtraGetInfluence{" +
                "player=" + getNickname() +
                ", indexIsland=" + indexIsland +
                '}';
    }
}
