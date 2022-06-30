package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;
/**
 * Server to client. Message sent to player a character card
 */


public class ReqPlayCharacterCardMessage extends Message {
    private static final long serialVersionUID = -668254545407621488L;
    private final List<CharacterCardModel> characterDeck;
    private final PlayerModel player;
    private final boolean existCard;
    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param active current player
     * @param characterDeck list of character cards
     */
    public ReqPlayCharacterCardMessage(PlayerModel active, List<CharacterCardModel> characterDeck, boolean existCard) {
        super(active.getNickname(), MessageType.REQ_PLAY_CHAR_CARD);
        this.characterDeck = characterDeck;
        player = active;
        this.existCard = existCard;
    }

    /**
     * @return the list of character cards
     */

    public List<CharacterCardModel> getCharacterDeck() {
        return characterDeck;
    }

    @Override
    public String toString() {
        return "ReqPlayCharacterCardMessage{" +
                "player=" + getNickname() +
                ", characterDeck=" + characterDeck +
                ", existCard=" + existCard +
                '}';
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public boolean getExistsCardPlayable() {
        return existCard;
    }
}
