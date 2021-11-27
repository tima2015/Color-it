package com.forward.colorit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Класс мини игры.
 */
public class SubGameGroup extends Group {

    private Actor subGameInfoActor;

    /**
     * Актёр содержащий какую либо информацию о текущей игре.
     * Может быть null.
     */
    public Actor getSubGameInfoActor() {
        return subGameInfoActor;
    }

    protected void setSubGameInfoActor(Actor subGameInfoActor) {
        this.subGameInfoActor = subGameInfoActor;
    }
}
