package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;
import com.forward.colorit.ui.action.CloudFlyAction;

/**
 * Экран меню.
 */
public class MenuScreen extends StageScreenAdapter {

    /**
     * Ширина окна просмотра для экрана меню
     */
    private static final int VIEWPORT_WIDTH = 1200;

    /**
     * Высота окна просмотра для экрана меню
     */
    private static final int VIEWPORT_HEIGHT = 675;

    private static final float cloudSpawnChance = 0.001f;

    public MenuScreen(){
        super(new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)), false);
        getStage().setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        MainMenu mainMenu = new MainMenu();
        getStage().addActor(mainMenu);
        Actor logo = new Label("Колорит", Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        getStage().addActor(logo);
        mainMenu.setPosition((getStage().getWidth() - mainMenu.getWidth())*.5f, (getStage().getHeight() - mainMenu.getHeight())*.5f);
        logo.setPosition((getStage().getWidth() - logo.getWidth())*.5f, mainMenu.getY() + mainMenu.getHeight() + Core.UI_PADDING_LARGE);
    }

    @Override
    public void show() {
        Core.core().setBackground(Core.core().getManager().get("background/backgroundColorForest.png", Texture.class));
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (MathUtils.random(0f, 1f) < cloudSpawnChance){
            CloudFlyAction action = new CloudFlyAction(Core.core().getBackgroundStage());
            Core.core().getBackgroundStage().addAction(action);
        }
        getStage().getViewport().apply();
        getStage().act(delta);
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height);
    }

}
