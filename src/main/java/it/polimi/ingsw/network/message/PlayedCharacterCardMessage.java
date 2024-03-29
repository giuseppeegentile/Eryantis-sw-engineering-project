package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.CharacterCardModel;

/**
 * Client to server. Message sent when playing a character card
 */

public class PlayedCharacterCardMessage extends Message {

    private static final long serialVersionUID = -3805305477253299296L;

    private final CharacterCardModel cardPlayed;

    /**
     * Default constructor
     * Parameters are set by the constructor
     * @param nickname current player
     * @param cardPlayed character card played
     */

    public PlayedCharacterCardMessage(String nickname, CharacterCardModel cardPlayed) {
        super(nickname, MessageType.CHARACTER_CARD_PLAYED);
        this.cardPlayed = cardPlayed;
    }

    /**
     * @return the character card played
     */

    public CharacterCardModel getCharacterPlayed() {
        return cardPlayed;
    }


    @Override
    public String toString() {
        return "PlayedCharacterCardMessage{" +
                "player=" + getNickname() +
                ", cardPlayed=" + cardPlayed +
                '}';
    }
}
