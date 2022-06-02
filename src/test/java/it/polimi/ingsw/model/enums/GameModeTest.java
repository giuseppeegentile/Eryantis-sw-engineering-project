package it.polimi.ingsw.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModeTest {


    @Test
    void enumTest(){
        assertEquals("BEGINNER", GameMode.BEGINNER.name());

        assertEquals("ADVANCED", GameMode.ADVANCED.toString());
    }
}