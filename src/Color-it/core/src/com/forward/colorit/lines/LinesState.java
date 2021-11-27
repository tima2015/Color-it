package com.forward.colorit.lines;

/**
 * Состояние игры "Линии".
 */
public enum LinesState {
    /**
     * Произвести вставку следующих шаров.
     */
    DO_INSERT,
    /**
     * Балы удалена линия.
     */
    LINE_DELETED,
    /**
     * Сетка заполнена.
     */
    GRID_FULL
}
