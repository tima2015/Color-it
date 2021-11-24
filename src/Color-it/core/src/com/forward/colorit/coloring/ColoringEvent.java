package com.forward.colorit.coloring;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;

public class ColoringEvent extends Event {
    public final int coloringCount;
    public final Color color;

    public ColoringEvent(int coloringCount, Color color) {
        this.coloringCount = coloringCount;
        this.color = color;
        setBubbles(true);
    }
}
