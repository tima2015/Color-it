package com.forward.colorit.snake;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.forward.colorit.SubGameGroup;

public class SnakeSubGame extends WidgetGroup implements SubGameGroup {

    private final Snake snake = new Snake();

    public SnakeSubGame(){
        addActor(snake);
        snake.setFillParent(true);
    }

    @Override
    public Actor getSubGameInfoActor() {
        return null;
    }
}
