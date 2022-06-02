package it.polimi.ingsw.model.colors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTowerTest {

    @Test
    void singleTest(){
        assertEquals("BLACK", ColorTower.BLACK.name());
        assertEquals("NULL", ColorTower.NULL.name());
        assertEquals("WHITE", ColorTower.WHITE.name());
        assertEquals("GREY", ColorTower.GREY.name());
    }
}