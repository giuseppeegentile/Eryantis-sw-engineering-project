package it.polimi.ingsw.model.colors;

import java.util.Random;

/**
 * Values of students pawn
 */
public enum ColorPawns {
    GREEN,
    RED,
    YELLOW,
    PINK,
    BLUE,
    NULL;


    /**
     *
     * @return A random ColorPawns in {RED, GREEN, BLUE, PINK, YELLOW}
     */
    public static ColorPawns getRandomColor(){
        return ColorPawns.values()[new Random().nextInt(ColorPawns.values().length - 1)];
    }

    /**
     *
     * @param color The color name of which you need to have the type in ColorPawns
     * @return The ColorPawn corresponding to the passed color
     */
    public static ColorPawns getEquivalentColorPawns(String color){
        return ColorPawns.valueOf(color);
    }
}
