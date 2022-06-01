package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.PlayerModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class AssistantCardModelTest {

    @Test
    void getOwnerPriorityMotherNatureMovement(){
        AssistantCardModel card = new AssistantCardModel(4, (byte)7);
        card.setOwner(new PlayerModel("Hopper"));
        assertEquals(7, card.getMotherNatureMovement());
        assertEquals(4, card. getPriority());
        assertEquals("Hopper", card.getOwner().getNickname());
        card.setMotherNatureMovement((byte)3);
        assertEquals(3, card.getMotherNatureMovement());
        card.setPriority(2);
        assertEquals(2, card.getPriority());
        AssistantCardModel card2 = new AssistantCardModel(4, new PlayerModel("Undi"), (byte)3);
        assertEquals("Undi", card2.getOwner().getNickname());
    }

}