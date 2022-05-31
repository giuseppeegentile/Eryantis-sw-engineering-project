package it.polimi.ingsw.network.message;

public class AskColorRemoveFromAll extends Message {

    private static final long serialVersionUID = 5650419507381102124L;

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
