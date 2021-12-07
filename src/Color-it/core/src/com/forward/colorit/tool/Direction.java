package com.forward.colorit.tool;

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

    Direction(int direction_x, int direction_y, float degree) {
        this.direction_x = direction_x;
        this.direction_y = direction_y;
        this.degree = degree;
    }
}
