package com.forward.colorit.snake;

import com.badlogic.gdx.math.Vector2;

public class SnakeFood {
    private final Vector2 pos = new Vector2();
    private final SnakeFoodType type;

    public SnakeFood(SnakeFoodType type) {
        this.type = type;
    }

    public Vector2 getPos() {
        return pos;
    }

    public SnakeFoodType getType() {
        return type;
    }
}
