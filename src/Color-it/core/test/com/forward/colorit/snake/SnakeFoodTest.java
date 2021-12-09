package com.forward.colorit.snake;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeFoodTest {

    @RepeatedTest(20)
    void getPos() {
        SnakeFood food = new SnakeFood(SnakeFoodType.BLUE);
        assertNotNull(food.getPos());
    }

    @RepeatedTest(20)
    void getType() {
        SnakeFoodType type = SnakeFoodType.getRandomFoodType();
        SnakeFood food = new SnakeFood(type);
        assertEquals(food.getType(), type);
    }
}