package com.forward.colorit.coloring;

import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ColoringGameScreenTest {

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void getLocalizedColorName(int i) {
        String[] color = new String[] { "#0000FF", "#FF0000", "#FFFF00", "#00FF00"};
        String[] result = new String[] { "Голубой", "Красный", "Желтый", "Зелёный"};
        Color c = Color.valueOf(color[i]);
        assertEquals(ColoringGameScreen.getLocalizedColorName(c), result[i]);
    }
}