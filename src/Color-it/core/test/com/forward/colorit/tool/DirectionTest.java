package com.forward.colorit.tool;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @RepeatedTest(20)
    void getRandomUpDownLeftOrRightDirection() {
        assertNotNull(Direction.getRandomUpDownLeftOrRightDirection());
    }
}