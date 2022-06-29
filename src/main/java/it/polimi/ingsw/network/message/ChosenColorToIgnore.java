package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
<<<<<<< HEAD

=======
/**
 * Message sent from client to server after the character card with effect (ignore color tower) has been played, and the color of the tower to ignore during influence count for this turn is chosen.
 */
>>>>>>> main
public class ChosenColorToIgnore extends Message{

    private static final long serialVersionUID = -2382150187009854544L;
    private final ColorPawns chosenColor;

    /**
<<<<<<< HEAD
     * Message sent to set chosen color to ignore
     * Parameters are set by the constructor
     * @param nickname active player
     * @param chosenColor colore chosen
     */

=======
     * @param nickname player that played the effect card.
     * @param chosenColor color of the tower chosen. To be ignored during influence count
     */
>>>>>>> main
    public ChosenColorToIgnore(String nickname, ColorPawns chosenColor) {
        super(nickname, MessageType.EFFECT_CARD_PLAYED);
        this.chosenColor = chosenColor;
    }

    /**
<<<<<<< HEAD
     * @return color chosen
     */

=======
     * @return color chosen color tower
     */
>>>>>>> main
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
