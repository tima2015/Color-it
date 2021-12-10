package com.forward.colorit.lines;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CellTextureStateTest {

    @RepeatedTest(20)
    void getRandomNotEmptyAndNotSelectedState() {
        CellTextureState state = CellTextureState.getRandomNotEmptyAndNotSelectedState();
        assertNotEquals(state, CellTextureState.EMPTY);
    }
}