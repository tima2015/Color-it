package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.forward.colorit.Core;

public class MenuScreen extends ScreenAdapter {

    private static final int VIEWPORT_WIDTH = 1200;
    private static final int VIEWPORT_HEIGHT = 675;
    private static final int SPACE = 24;

    private Stage stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
    private MainMenu mainMenu = new MainMenu();
    private Actor logo = new Label("Колорит", Core.core().getUi(), Core.LABEL_STYLE_LARGE);

    public MenuScreen(){
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        stage.addActor(mainMenu);
        stage.addActor(logo);
        mainMenu.setPosition((stage.getWidth() - mainMenu.getWidth())*.5f, (stage.getHeight() - mainMenu.getHeight())*.5f);
        logo.setPosition((stage.getWidth() - logo.getWidth())*.5f, mainMenu.getY() + mainMenu.getHeight() + SPACE);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
