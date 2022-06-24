package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;

public class ChosenColorToIgnore extends Message{
    private static final long serialVersionUID = -2382150187009854544L;

    private final ColorPawns chosenColor;

    /**
     * Message sent to set chosen color to ignore
     * Parameters are set by the constructor
     * @param nickname active player
     * @param chosenColor colore chosen
     */

    public ChosenColorToIgnore(String nickname, ColorPawns chosenColor) {
        super(nickname, MessageType.EFFECT_CARD_PLAYED);
        this.chosenColor = chosenColor;
    }

    /**
     * @return color chosen
     */

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
