package com.forward.colorit.coloring;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;

public class ColoringGameScreen extends ScreenAdapter {

    private static final int VIEWPORT_WIDTH = 1600;
    private static final int VIEWPORT_HEIGHT = 900;
    private static final int MARGINS = 24;
    private static final int PADDING = 12;


    private Stage mainStage;
    private Actor subGame;
    private Actor image;
    private Group gameInfo;

    public ColoringGameScreen(Actor subGame, Actor image) {
        this.subGame = subGame;
        this.image = image;
        gameInfo = new Window("", Core.core().getUi());
    }

    @Override
    public void show() {
        mainStage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        mainStage.addActor(subGame);
        mainStage.addActor(image);
        mainStage.addActor(gameInfo);

        //настройка элементов todo вынести в отдельные методы
        //subgame
        float subGameSize = VIEWPORT_HEIGHT - 2 * MARGINS;
        subGame.setSize(subGameSize, subGameSize);
        subGame.setPosition(MARGINS, MARGINS);
        //todo add ColoringEventListener (EventListener with checks (event instanceof ColoringEvent))

        float imageSize = VIEWPORT_WIDTH - (subGame.getX() + subGame.getWidth() + PADDING + MARGINS);
        image.setSize(imageSize,imageSize);
        image.setPosition(subGame.getX() + subGame.getWidth() + PADDING,VIEWPORT_HEIGHT - imageSize - MARGINS);

        gameInfo.setSize(image.getWidth(), VIEWPORT_HEIGHT - (image.getHeight() + PADDING + MARGINS));
        gameInfo.setPosition(image.getX(), subGame.getY());
    }

    @Override
    public void render(float delta) {

        mainStage.act(delta);
        mainStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        mainStage.dispose();
    }
}
