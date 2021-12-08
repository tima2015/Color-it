package com.forward.colorit.snake;

import com.badlogic.gdx.math.Vector2;
import com.forward.colorit.tool.Direction;

public class Point {

    private final Vector2 pos = new Vector2();
    private Direction direction;

    public Point(Direction direction) {
        this.direction = direction;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
