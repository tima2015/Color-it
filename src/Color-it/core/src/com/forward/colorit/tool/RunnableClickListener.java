package com.forward.colorit.tool;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class RunnableClickListener extends ClickListener implements Runnable{

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        run();
    }
}
