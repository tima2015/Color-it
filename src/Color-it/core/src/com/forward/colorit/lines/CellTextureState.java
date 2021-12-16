package com.forward.colorit.lines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.forward.colorit.Core;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Состояния ячеек
 */
public enum CellTextureState {


    EMPTY("lines_cell", Color.BLACK),
    RED("lines_red", Color.RED),
    BLUE("lines_blue", Color.BLUE),
    GREEN("lines_green", Color.GREEN),
    YELLOW("lines_yellow", Color.YELLOW);

    private String region;
    public final Color color;

    CellTextureState(String region, Color color) {
        this.region = region;
        this.color = color;
    }

    /**
     * @return Текстуру для текущего состояния.
     */
    TextureRegion getRegion() {
        return Core.core().getTextures().findRegion(region);
    }

    /**
     * @return Случайное невыделеное и не пустое состояние.
     */
    private static final String TAG = "CellTextureState";

    public static CellTextureState getRandomNotEmptyAndNotSelectedState() {
        Gdx.app.debug(TAG, "getRandomNotEmptyAndNotSelectedState() called");
        List<CellTextureState> states = Arrays.asList(RED, BLUE, GREEN, YELLOW);
        Collections.shuffle(states, MathUtils.random);
        return states.get(0);
    }
}
