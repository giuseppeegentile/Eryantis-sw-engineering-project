package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.CharacterCardModel;

public class PlayedCharacterCardMessage extends Message {

    private static final long serialVersionUID = -3805305477253299296L;

    private final CharacterCardModel cardPlayed;

    public PlayedCharacterCardMessage(String nickname, CharacterCardModel cardPlayed) {
        super(nickname, MessageType.CHARACTER_CARD_PLAYED);
        this.cardPlayed = cardPlayed;
    }

    public CharacterCardModel getCharacterPlayed() {
        return cardPlayed;
    }


    @Override
    public String toString() {
        return "PlayedCharacterCardMessage{" +
                "player=" + cardPlayed +
                ", cardPlayed=" + cardPlayed +
                '}';
    }
}
