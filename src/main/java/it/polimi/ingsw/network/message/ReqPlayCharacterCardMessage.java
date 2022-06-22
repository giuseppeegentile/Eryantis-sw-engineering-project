package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class ReqPlayCharacterCardMessage extends Message {
    private static final long serialVersionUID = -668254545407621488L;
    private final List<CharacterCardModel> characterDeck;
    private final PlayerModel player;

    /**
     * Message sent to player a character card
     * @param active current player
     * @param characterDeck list of character cards
     */
    public ReqPlayCharacterCardMessage(PlayerModel active, List<CharacterCardModel> characterDeck) {
        super(active.getNickname(), MessageType.REQ_PLAY_CHAR_CARD);
        this.characterDeck = characterDeck;
        player = active;
    }

    public List<CharacterCardModel> getCharacterDeck() {
        return characterDeck;
    }

    @Override
    public String toString() {
        return "ReqPlayCharacterCardMessage{" +
                "player=" + getNickname() +
                ", characterDeck=" + characterDeck +
                '}';
    }

    public PlayerModel getPlayer() {
        return player;
    }
}
