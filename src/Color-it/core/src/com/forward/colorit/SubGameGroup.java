package com.forward.colorit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Класс мини игры.
 */
public class SubGameGroup extends Group {

    /**
     * Актёр содержащий какую либо информацию о текущей игре
     */
    private Actor subGameInfoActor;

    public Actor getSubGameInfoActor() {
        return subGameInfoActor;
    }

    protected void setSubGameInfoActor(Actor subGameInfoActor) {
        this.subGameInfoActor = subGameInfoActor;
    }
}
