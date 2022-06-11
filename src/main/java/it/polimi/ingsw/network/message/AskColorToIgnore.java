package it.polimi.ingsw.network.message;


public class AskColorToIgnore extends Message {

    private static final long serialVersionUID = -7419976465222022178L;

    public AskColorToIgnore(String active) {
        super(active, MessageType.ASK_COLOR_TO_IGNORE);
    }

    @Override
    public String toString() {
        return "AskColorToIgnore{" +
                "player=" + getNickname() +
                '}';
    }
}
