package com.forward.colorit.lines;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.forward.colorit.Core;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CellTextureState {
    EMPTY("empty_cell", Color.BLACK),
    RED("red", Color.RED),
    BLUE("blue", Color.BLUE),
    GREEN("green", Color.GREEN),
    YELLOW("yellow", Color.YELLOW),
    RED_SELECTED("red_selected", Color.RED),
    BLUE_SELECTED("blue_selected", Color.BLUE),
    GREEN_SELECTED("green_selected", Color.GREEN),
    YELLOW_SELECTED("yellow_selected", Color.YELLOW);

    private String region;
    public final Color color;

    CellTextureState(String region, Color color){
        this.region = region;
        this.color = color;
    }

    TextureRegion getRegion(){
        return Core.core().getTextures().findRegion(region);
    }

    public static CellTextureState getRandomNotEmptyAndNotSelectedState(){
        List<CellTextureState> states = Arrays.asList(RED, BLUE, GREEN, YELLOW);
        Collections.shuffle(states, MathUtils.random);
        return states.get(0);
    }
}
