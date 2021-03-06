package com.forward.colorit.tool;

import com.badlogic.gdx.utils.Array;

/**
 * Перечисление направлений движений.
 */
public enum Direction {
    DOWN(0, -1, 270),
    LEFT(-1, 0, 180),
    LEFT_DOWN(-1, -1, 225),
    LEFT_UP(-1, 1, 135),
    RIGHT(1, 0, 0),
    RIGHT_DOWN(1, -1, 315),
    RIGHT_UP(1, 1, 45),
    UP(0, 1, 90);

    public final int direction_x, direction_y;
    public final float degree;

    /**
     * @param direction_x Направление по x
     * @param direction_y Направление по y
     * @param degree градусы направления
     */
    Direction(int direction_x, int direction_y, float degree) {
        this.direction_x = direction_x;
        this.direction_y = direction_y;
        this.degree = degree;
    }

    /**
     * @return случайное направление
     */
    public static Direction getRandomUpDownLeftOrRightDirection(){
        Array<Direction> directions = new Array<>(new Direction[]{UP,DOWN, RIGHT, LEFT});
        directions.shuffle();
        return directions.get(0);
    }
}
