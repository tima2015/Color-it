package com.forward.colorit.snake;

import com.badlogic.gdx.utils.Array;

public enum SnakeFoodType {
    RED,
    GREEN,
    BLUE,
    GRAY;

    public static SnakeFoodType getRandomFoodType(){
        Array<SnakeFoodType> types = new Array<>(new SnakeFoodType[]{RED, GREEN, BLUE, GRAY});
        types.shuffle();
        return types.get(0);
    }
}
