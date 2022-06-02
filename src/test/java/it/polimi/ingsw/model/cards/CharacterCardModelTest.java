package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.game.GameController;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.effects.AddInfluenceEffect;
import it.polimi.ingsw.model.effects.AddToHallEffect;
import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterCardModelTest {

    @Test
    void CharacterCard(){
        AddInfluenceEffect effect = new AddInfluenceEffect(new GameController());
        CharacterCardModel card = new CharacterCardModel(1, effect, 6);
        card.setOwner(new PlayerModel("Dustin"));
        assertEquals(6, card.getCharacterId());
        assertEquals(effect, card.getEffect());
        assertTrue(card.enoughCoins());
        assertEquals(1, card.getMoneyOnCard());
        assertEquals("Dustin", card.getOwner().getNickname());
        AddToHallEffect effect2 = new AddToHallEffect(asList(ColorPawns.BLUE, ColorPawns.RED));
        card.setEffect(effect2);
        assertEquals(effect2, card.getEffect());

        int oldMoney = card.getMoneyOnCard();
        card.incrementMoneyCost();
        assertEquals(oldMoney+1, card.getMoneyOnCard());

        //card.incrementMoneyCost();
        //assertEquals(2, card.getMoneyOnCard());
        //card.setMoneyOnCard(3);
        //assertEquals(3, card.getMoneyOnCard());
        //card.setCharacterId(7);
        //assertEquals(7, card.getCharacterId());
    }
}