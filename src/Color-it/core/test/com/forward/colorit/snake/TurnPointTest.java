package com.forward.colorit.snake;

import com.forward.colorit.tool.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnPointTest {

    @Test
    void getFrom() {
        Direction dir = Direction.getRandomUpDownLeftOrRightDirection();
        TurnPoint turnPoint = new TurnPoint(Direction.DOWN, dir);
        assertEquals(turnPoint.getFrom(), dir);
    }

    @Test
    void setFrom() {
        Direction dir = Direction.getRandomUpDownLeftOrRightDirection();
        TurnPoint turnPoint = new TurnPoint(Direction.DOWN, Direction.UP);
        turnPoint.setFrom(dir);
        assertEquals(turnPoint.getFrom(), dir);
    }
}