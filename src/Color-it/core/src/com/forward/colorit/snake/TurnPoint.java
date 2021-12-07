package com.forward.colorit.snake;

import com.forward.colorit.tool.Direction;

public class TurnPoint extends Point{
    private Direction from;

    public TurnPoint(Direction direction, Direction from) {
        super(direction);
        this.from = from;
    }

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }
}
