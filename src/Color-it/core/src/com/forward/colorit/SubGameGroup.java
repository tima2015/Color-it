package com.forward.colorit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

/**
 * Интерфейс мини игры.
 */
public interface SubGameGroup {

    /**
     * Актёр содержащий какую либо информацию о текущей игре.
     * Может быть null.
     */
    Actor getSubGameInfoActor();
}
