package com.forward.colorit.snake;

import com.forward.colorit.tool.Direction;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getPos() {
        Point point = new Point(Direction.DOWN);
        assertNotNull(point.getPos());
    }

    @RepeatedTest(20)
    void getDirection() {
        Direction dir = Direction.getRandomUpDownLeftOrRightDirection();
        Point point = new Point(dir);
        assertEquals(point.getDirection(), dir);
    }

    @RepeatedTest(20)
    void setDirection() {
        Direction dir = Direction.getRandomUpDownLeftOrRightDirection();
        Point point = new Point(Direction.DOWN);
        point.setDirection(dir);
        assertEquals(point.getDirection(), dir);
    }
}