package it.polimi.ingsw.network.message;


import it.polimi.ingsw.model.cards.AssistantCardModel;

import java.util.List;

//da completare
public class DisplayCemeteryMessage extends Message{
    private static final long serialVersionUID = -7509713511298375285L;

    private final List<AssistantCardModel> cemetery;
    public DisplayCemeteryMessage(String nickname, List<AssistantCardModel> cemetery) {
        super(nickname, MessageType.DISPLAY);
        this.cemetery = cemetery;
    }

    public List<AssistantCardModel> getCemetery() {
        return cemetery;
    }

    @Override
    public String toString() {
        return "ShowCemeteryMessage{" +
                "player" + getNickname() +
                "cemetery=" + cemetery +
                '}';
    }
}
