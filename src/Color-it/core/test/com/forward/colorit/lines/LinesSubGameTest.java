package com.forward.colorit.lines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.forward.colorit.Core;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LinesSubGameTest {

    @Disabled
    @Test
    void isNextCellNotEquals() {
        LinesSubGame game = new LinesSubGame();
        game.isNextCellNotEquals();
        int j = 0;
        do {
            for (int i = 0; i < game.getNextCellsCount(); i++) {
                int rand = MathUtils.random(LinesSubGame.CELL_COUNT - 1);
                game.getNextCells()[i] = game.getCell(rand / LinesSubGame.FIELD_SIZE, rand % LinesSubGame.FIELD_SIZE);
            }
        } while (!game.isNextCellNotEquals() || ++j < 1000);
        assertTrue(game.isNextCellNotEquals());
    }

    @Disabled
    @Test
    void isNextCellsEmpty() {
        LinesSubGame game = new LinesSubGame();
        game.isNextCellNotEquals();
        int j = 0;
        do {
            for (int i = 0; i < game.getNextCellsCount(); i++) {
                int rand = MathUtils.random(LinesSubGame.CELL_COUNT - 1);
                game.getNextCells()[i] = game.getCell(rand / LinesSubGame.FIELD_SIZE, rand % LinesSubGame.FIELD_SIZE);
            }
        } while (!game.isNextCellsEmpty() || ++j < 1000);
        assertTrue(game.isNextCellsEmpty());
    }

    @Disabled
    @Test
    void getCellPosition() {
        LinesSubGame game = new LinesSubGame();
        LinesCell[][] cells = game.getSubGameCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                GridPoint2 cellPosition = game.getCellPosition(cells[i][j]);
                assertEquals(cellPosition.x, i);
                assertEquals(cellPosition.y, j);
            }
        }
    }

    @Disabled
    @Test
    void getSubGameInfoActor() {
        assertNotNull(new LinesSubGame().getSubGameInfoActor());
    }
}