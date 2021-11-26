package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;

public class MenuScreen extends StageScreenAdapter {

    private static final int VIEWPORT_WIDTH = 1200;
    private static final int VIEWPORT_HEIGHT = 675;
    private static final int SPACE = 24;

    private MainMenu mainMenu = new MainMenu();
    private Actor logo = new Label("Колорит", Core.core().getUi(), Core.LABEL_STYLE_LARGE);

    public MenuScreen(){
        super(new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)), false);
        getStage().setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        getStage().addActor(mainMenu);
        getStage().addActor(logo);
        mainMenu.setPosition((getStage().getWidth() - mainMenu.getWidth())*.5f, (getStage().getHeight() - mainMenu.getHeight())*.5f);
        logo.setPosition((getStage().getWidth() - logo.getWidth())*.5f, mainMenu.getY() + mainMenu.getHeight() + SPACE);
    }

    @Override
    public void show() {
        Core.core().setBackground(Core.core().getManager().get("background/backgroundColorForest.png", Texture.class));
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void render(float delta) {
        getStage().getViewport().apply();
        getStage().act(delta);
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height);
    }

}
