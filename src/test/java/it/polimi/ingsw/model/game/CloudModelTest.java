package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.colors.ColorPawns;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloudModelTest {


    @Test
    void testCloud() {
        CloudModel c;
        c = new CloudModel(3);
        List<ColorPawns> students = List.of(ColorPawns.RED, ColorPawns.BLUE, ColorPawns.PINK);
        c.setStudents(students);

        assertEquals(List.of(ColorPawns.RED, ColorPawns.BLUE, ColorPawns.PINK), c.getStudents());
        c.cleanStudent();
        assertEquals(List.of(), c.getStudents());
    }

}