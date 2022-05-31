package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

public class ChosenColorToIgnore extends Message{
    private static final long serialVersionUID = -2382150187009854544L;

    private final ColorPawns chosenColor;

    public ChosenColorToIgnore(String nickname, ColorPawns chosenColor) {
        super(nickname, MessageType.CHARACTER_CARD_PLAYED);
        this.chosenColor = chosenColor;
    }

    public ColorPawns getChosenColor() {
        return chosenColor;
    }

    @Override
    public String toString() {
        return "ChosenColorToIgnore{" +
                "player=" + getNickname() +
                ", chosenColor=" + chosenColor +
                '}';
    }
}
