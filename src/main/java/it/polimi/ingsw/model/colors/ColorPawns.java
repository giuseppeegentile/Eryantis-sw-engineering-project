package it.polimi.ingsw.model.colors;

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

    public static ColorPawns getEquivalentColorPawns(String color){
        return ColorPawns.valueOf(color);
    }
}
