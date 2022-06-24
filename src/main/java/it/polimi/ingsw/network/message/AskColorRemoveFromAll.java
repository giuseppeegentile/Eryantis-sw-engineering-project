package it.polimi.ingsw.network.message;

public class AskColorRemoveFromAll extends Message {

    private static final long serialVersionUID = 5650419507381102124L;

    /**
     * Message sent to remove a color for every player
     * Parameter is set by the constructor
     * @param active active player
     */

    public AskColorRemoveFromAll(String active) {
        super(active, MessageType.COLOR_REMOVE_ALL);
    }

    @Override
    public String toString() {
        return "AskColorRemoveFromAll{" +
                "player=" + getNickname() +
                '}';
    }
}
