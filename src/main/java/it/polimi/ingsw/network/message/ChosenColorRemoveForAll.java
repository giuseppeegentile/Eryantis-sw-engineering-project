package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.colors.ColorPawns;

public class ChosenColorRemoveForAll extends Message {

    private static final long serialVersionUID = -8490777083842367071L;
    private final ColorPawns color;

    public ChosenColorRemoveForAll(String active, ColorPawns equivalentColorPawns) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.color = equivalentColorPawns;
    }

    public ColorPawns getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ChosenColorRemoveForAll{" +
                "player=" + getNickname() +
                "color=" + color +
                '}';
    }
}
