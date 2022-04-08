package it.polimi.ingsw.model;

import java.util.Random;

public enum ColorPawns {
    GREEN,
    RED,
    YELLOW,
    PINK,
    BLUE,
    NULL;


    public static ColorPawns getRandomColor(){
        return ColorPawns.values()[new Random().nextInt(ColorPawns.values().length - 1)];
    }
}
