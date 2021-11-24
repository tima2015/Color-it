package com.forward.colorit.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.forward.colorit.Core;
import com.forward.colorit.lines.LinesSubGame;

public class SubGameTest extends ScreenAdapter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = config.height = 500;
        config.resizable = true;
        Core core = new Core(new SubGameTest());
        new LwjglApplication(core, config);
    }

    private Stage stage;

    @Override
    public void show() {
        LinesSubGame actor = new LinesSubGame();
        stage = new Stage(new FitViewport(actor.getWidth(), actor.getHeight()));
        stage.addActor(actor);
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
