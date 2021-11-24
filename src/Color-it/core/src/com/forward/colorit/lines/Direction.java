package com.forward.colorit.lines;

enum Direction {
    DOWN(0, -1),
    LEFT(-1, 0),
    LEFT_DOWN(-1, -1),
    LEFT_UP(-1, 1),
    RIGHT(1, 0),
    RIGHT_DOWN(1, -1),
    RIGHT_UP(1, 1),
    UP(0, 1);

    public final int direction_x, direction_y;

    Direction(int direction_x, int direction_y) {
        this.direction_x = direction_x;
        this.direction_y = direction_y;
    }
}
