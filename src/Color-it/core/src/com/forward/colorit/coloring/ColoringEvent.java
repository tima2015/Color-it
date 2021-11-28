package com.forward.colorit.coloring;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Событие о закрашивании рисунка.
 */
public class ColoringEvent extends Event {

    /**
     * Количество фрагменов для закраски.
     */
    public final int coloringCount;
    /**
     * Цвет вызова.
     */
    public final Color color;

    public ColoringEvent(int coloringCount, Color color) {
        this.coloringCount = coloringCount;
        this.color = color;
        setBubbles(true);
    }

    @Override
    public String toString() {
        return "ColoringEvent{" +
                "coloringCount=" + coloringCount +
                ", color=" + color +
                '}';
    }
}
