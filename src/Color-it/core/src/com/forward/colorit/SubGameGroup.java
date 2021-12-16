package com.forward.colorit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

/**
 * Интерфейс мини игры.
 */
public interface SubGameGroup {

    /**
     * @return Актёр содержащий какую либо информацию о текущей игре.
     */
    Actor getSubGameInfoActor();

    /**
     * @return Актёр содержащий инструкцию к игре
     */
    Actor getSubGameInstructionActor();
}
