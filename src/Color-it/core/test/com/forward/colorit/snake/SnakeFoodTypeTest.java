package com.forward.colorit.snake;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeFoodTypeTest {

    @RepeatedTest(20)
    void getRandomFoodType() {
        assertNotNull(SnakeFoodType.getRandomFoodType());
    }
}