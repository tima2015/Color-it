package com.forward.colorit.snake;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.forward.colorit.Core;
import com.forward.colorit.SubGameGroup;

public class SnakeSubGame extends WidgetGroup {

    private final Snake snake = new Snake();
    private final Image background = new Image(Core.core().getUi().getDrawable("GRAY_6_PIXEL"));//new Image(new TiledDrawable(Core.core().getTextures().findRegion("snake_grid_cell")));

    public SnakeSubGame(){
        addActor(background);
        addActor(snake);
        background.toBack();
        background.setFillParent(true);
        snake.toFront();
        snake.setFillParent(true);
    }

    public Actor getSubGameInfoActor() {
        return new Actor();
    }
}
