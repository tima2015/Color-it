package com.forward.colorit.lines;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Queue;
import com.forward.colorit.coloring.ColoringEvent;
import com.forward.colorit.coloring.GameEndEvent;

public class LinesSubGame extends Group {

    private final static int FIELD_SIZE = 9;
    private final static int CELL_SIZE = 40;
    private final static int CELL_COUNT = FIELD_SIZE * FIELD_SIZE;
    private final static int BALL_INSERT_COUNT = 3;

    private final LinesCell[][] cells = new LinesCell[FIELD_SIZE][FIELD_SIZE];
    private final LinesCell[] nextCells = new LinesCell[BALL_INSERT_COUNT];
    private final CellTextureState[] nextCellTextureState = new CellTextureState[BALL_INSERT_COUNT];
    private LinesCell selected;

    private Actor additionalInfoActor = new Actor();//todo актёр отображающий следующую вставку

    public LinesSubGame() {
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new LinesCell();
                cells[i][j].addListener(new LineSubGameClickListener());
                addActor(cells[i][j]);
            }
        setSize(FIELD_SIZE * CELL_SIZE, FIELD_SIZE * CELL_SIZE);
        initWithRandNextCells();
        insertNextCells();
        setDebug(Gdx.app.getLogLevel() == Application.LOG_DEBUG, true);
    }

    @Override
    public void setSize(float width, float height) {
        float nCellSizeH = width/(float) FIELD_SIZE;
        float nCellSizeV = height/(float) FIELD_SIZE;
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setPosition(i * nCellSizeH, j * nCellSizeV);
                cells[i][j].setSize(nCellSizeH, nCellSizeV);
            }
        super.setSize(width, height);
    }

    private boolean isNextCellNotEquals() {
        return !(nextCells[0].equals(nextCells[1])
                || nextCells[1].equals(nextCells[2])
                || nextCells[0].equals(nextCells[2]));
    }

    private boolean isNextCellsEmpty() {
        return nextCells[0].getState() == CellTextureState.EMPTY
                && nextCells[1].getState() == CellTextureState.EMPTY
                && nextCells[2].getState() == CellTextureState.EMPTY;
    }

    private void initWithRandNextCells() {
        for (int i = 0; i < BALL_INSERT_COUNT; i++)
            nextCellTextureState[i] = CellTextureState.getRandomNotEmptyAndNotSelectedState();
    }

    private void insertNextCells() {
        do {
            for (int i = 0; i < nextCells.length; i++) {
                int rand = MathUtils.random(CELL_COUNT - 1);
                nextCells[i] = cells[rand / FIELD_SIZE][rand % FIELD_SIZE];
            }
        } while (!isNextCellNotEquals() || !isNextCellsEmpty());

        for (int i = 0; i < nextCells.length; i++) nextCells[i].setState(nextCellTextureState[i]);

        initWithRandNextCells();
    }

    private GridPoint2 getCellPosition(LinesCell cell) {
        for (int x = 0; x < cells.length; x++)
            for (int y = 0; y < cells[x].length; y++)
                if (cells[x][y].equals(cell))
                    return new GridPoint2(x, y);
        throw new RuntimeException("An attempt to get the coordinates of a cell not included in the grid!");
    }

    private int sameStatesInDirection(GridPoint2 pos, Direction direction) {
        try {
            GridPoint2 next = new GridPoint2(pos.x + direction.direction_x, pos.y + direction.direction_y);
            if (cells[pos.x][pos.y].getState() == cells[next.x][next.y].getState()) {
                return sameStatesInDirection(next, direction) + 1;
            }
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
        return 0;
    }

    private void findLines(LinesCell cell) {
        GridPoint2 pos = getCellPosition(cell);
        //горизонталь
        int s1 = sameStatesInDirection(pos, Direction.LEFT);
        int s2 = sameStatesInDirection(pos, Direction.RIGHT);
        if (s1 + s2 > BALL_INSERT_COUNT) {
            for (int x = pos.x - s1; x <= pos.x + s2; x++) cells[x][pos.y].setVisited(true);
            fire(new ColoringEvent(s1 + s2 + 1, cells[pos.x][pos.y].getState().color));
        }

        //Вертикаль
        s1 = sameStatesInDirection(pos, Direction.DOWN);
        s2 = sameStatesInDirection(pos, Direction.UP);
        if (s1 + s2 > BALL_INSERT_COUNT) {
            for (int y = pos.y - s1; y <= pos.y + s2; y++) cells[pos.x][y].setVisited(true);
            fire(new ColoringEvent(s1 + s2 + 1, cells[pos.x][pos.y].getState().color));
        }

        //Диагональ /
        s1 = sameStatesInDirection(pos, Direction.LEFT_DOWN);
        s2 = sameStatesInDirection(pos, Direction.RIGHT_UP);
        if (s1 + s2 > BALL_INSERT_COUNT) {
            for (int x = pos.x - s1; x <= pos.x + s2; x++)
                for (int y = pos.y - s1; y <= pos.y + s2; y++)
                    cells[x][y].setVisited(true);
            fire(new ColoringEvent(s1 + s2 + 1, cells[pos.x][pos.y].getState().color));
        }

        //Диагональ \
        s1 = sameStatesInDirection(pos, Direction.RIGHT_DOWN);
        s2 = sameStatesInDirection(pos, Direction.LEFT_UP);
        if (s1 + s2 > BALL_INSERT_COUNT) {
            for (int x = pos.x - s2; x <= pos.x + s1; x++)
                for (int y = pos.y - s1; y <= pos.y + s2; y++)
                    cells[x][y].setVisited(true);
            fire(new ColoringEvent(s1 + s2 + 1, cells[pos.x][pos.y].getState().color));
        }
    }

    private boolean deleteLines() {
        boolean isDelete = false;
        for (LinesCell[] cell : cells)
            for (LinesCell linesCell : cell) {
                if (!linesCell.isVisited()) continue;
                linesCell.setVisited(false);
                linesCell.setState(CellTextureState.EMPTY);
                isDelete = true;
            }
        return isDelete;
    }

    private LinesState checkGame(LinesCell cell) {
        findLines(cell);
        if (deleteLines()) return LinesState.LINE_DELETED;

        int empty = 0;
        for (LinesCell[] linesCells : cells)
            for (LinesCell linesCell : linesCells)
                if (linesCell.getState() == CellTextureState.EMPTY)
                    empty++;

        if (empty < 4)
            return LinesState.GRID_FULL;
        return LinesState.DO_INSERT;
    }

    private void unmarkCells() {
        for (LinesCell[] cell : cells)
            for (LinesCell linesCell : cell)
                linesCell.setVisited(false);
    }

    private boolean checkingDirection(Direction direction, GridPoint2 current, GridPoint2 end, Queue<LinesCell> queue) {
        try {
            GridPoint2 next = new GridPoint2(current).add(direction.direction_x, direction.direction_y);
            LinesCell cell = cells[next.x][next.y];
            if (cell.getState() == CellTextureState.EMPTY)
                if (!cell.isVisited()) {
                    queue.addLast(cell);
                    cell.setVisited(true);
                    if (next.equals(end)) {
                        unmarkCells();
                        return true;
                    }
                }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private boolean isMovable(LinesCell to) {
        GridPoint2 end = getCellPosition(to);
        Queue<LinesCell> queue = new Queue<>();
        queue.addLast(selected);
        while (queue.size > 0) {
            LinesCell cell = queue.removeFirst();

            GridPoint2 current = getCellPosition(cell);

            if (current.equals(end)) {
                unmarkCells();
                return true;
            }

            cell.setVisited(true);
            if (checkingDirection(Direction.LEFT, current, end, queue)
                    || checkingDirection(Direction.RIGHT, current, end, queue)
                    || checkingDirection(Direction.UP, current, end, queue)
                    || checkingDirection(Direction.DOWN, current, end, queue)) {
                unmarkCells();
                return true;
            }
        }

        unmarkCells();
        return false;
    }

    private class LineSubGameClickListener extends ClickListener {

        private static final String TAG = "LineSubGameClickListene";

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (!(event.getListenerActor() instanceof LinesCell)) return;
            LinesCell a = (LinesCell) event.getListenerActor();
            if (selected == null) {
                if (a.getState() == CellTextureState.EMPTY) return;
                selected = a;
                selected.setSelected(true);
            } else if (a.getState() != CellTextureState.EMPTY) {
                if (selected.equals(a)) {
                    selected.setSelected(!selected.isSelected());
                } else {
                    selected.setSelected(false);
                    selected = a;
                    selected.setSelected(true);
                }
            } else if (isMovable(a)) {
                selected.setSelected(false);
                a.setState(selected.getState());
                selected.setState(CellTextureState.EMPTY);
                selected = null;

                LinesState state = checkGame(a);
                if (state == LinesState.GRID_FULL) fire(new GameEndEvent(false));
                else if (state == LinesState.DO_INSERT) insertNextCells();
            }
        }
    }
}
