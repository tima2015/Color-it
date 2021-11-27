package com.forward.colorit.coloring;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Событие, сообщающие о проигрыше.
 */
public class GameEndEvent extends Event {
    public final boolean win;

    public GameEndEvent(boolean win) {
        this.win = win;
        setBubbles(true);
    }
}
