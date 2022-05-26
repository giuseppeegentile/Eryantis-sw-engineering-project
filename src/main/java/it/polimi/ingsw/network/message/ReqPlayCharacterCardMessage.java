package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.CharacterCardModel;

import java.util.List;

public class ReqPlayCharacterCardMessage extends Message {
    private static final long serialVersionUID = -668254545407621488L;
    private final List<CharacterCardModel> characterDeck;
    public ReqPlayCharacterCardMessage(String active, List<CharacterCardModel> characterDeck) {
        super(active, MessageType.REQ_PLAY_CHAR_CARD);
        this.characterDeck = characterDeck;
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
}
