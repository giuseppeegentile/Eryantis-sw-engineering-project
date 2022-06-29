package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.colors.ColorPawns;

<<<<<<< HEAD
=======
/**
 * Message from client to sever, where who played the effect card to remove 3 student of a certain color, send the color to the server.
 */
>>>>>>> main
public class ChosenColorRemoveForAll extends Message {

    private static final long serialVersionUID = -8490777083842367071L;
    private final ColorPawns color;

    /**
     * Message sent to set the chosen color to remove
<<<<<<< HEAD
     * Parameters are set by the constructor
=======
>>>>>>> main
     * @param active active player
     * @param equivalentColorPawns color to remove
     */

    public ChosenColorRemoveForAll(String active, ColorPawns equivalentColorPawns) {
        super(active, MessageType.EFFECT_CARD_PLAYED);
        this.color = equivalentColorPawns;
    }

    /**
     * @return the color chosen
     */
<<<<<<< HEAD

=======
>>>>>>> main
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
