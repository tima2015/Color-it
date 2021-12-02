package com.forward.colorit.tool;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RunnableClickListener extends ClickListener{

    private final Runnable runnable;

    public RunnableClickListener(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        runnable.run();
    }
}
