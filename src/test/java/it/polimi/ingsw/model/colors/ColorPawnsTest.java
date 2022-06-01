package it.polimi.ingsw.model.colors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorPawnsTest {

    @Test
    void ColorPawns(){
        assertNotEquals(ColorPawns.NULL, ColorPawns.getRandomColor());
        assertEquals(ColorPawns.BLUE, ColorPawns.getEquivalentColorPawns("BLUE"));
    }
}