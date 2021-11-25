package com.forward.colorit.coloring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;

public class ColoringGameScreen extends ScreenAdapter {

    private static final int VIEWPORT_WIDTH = 1600;
    private static final int VIEWPORT_HEIGHT = 1000;
    private static final int MARGINS = 24;
    private static final int PADDING = 12;


    private Stage stage;
    private Actor subGame;
    private ColoringLevelData data;
    private ColoringImage image;
    private Group gameInfo;

    public ColoringGameScreen(Actor subGame, ColoringLevelData data) {
        this.subGame = subGame;
        this.data = data;
        this.image = new ColoringImage(data.getImg());
        gameInfo = new Window("", Core.core().getUi());
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        stage.addActor(subGame);
        stage.addActor(image);
        stage.addActor(gameInfo);

        //настройка элементов todo вынести в отдельные методы
        //subgame
        float subGameSize = VIEWPORT_HEIGHT - 2 * MARGINS;
        subGame.setSize(subGameSize, subGameSize);
        subGame.setPosition(MARGINS, MARGINS);
        subGame.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ColoringEvent){
                    ColoringEvent cEvent = (ColoringEvent) event;
                    for (int i = 0; i < data.getMap().length; i++) {
                        if (data.getMap()[i] != null && Color.valueOf(data.getMap()[i].getColor()).equals(cEvent.color)) {
                            image.color(data.getMap()[i]);
                            data.getMap()[i] = null;
                            return true;
                        }
                    }
                }
                if (event instanceof GameEndEvent){
                    // TODO: 25.11.2021
                }
                return false;
            }
        });
        //todo add ColoringEventListener (EventListener with checks (event instanceof ColoringEvent))

        float imageSize = VIEWPORT_WIDTH - (subGame.getX() + subGame.getWidth() + PADDING + MARGINS);
        image.setSize(imageSize,imageSize);
        image.setPosition(subGame.getX() + subGame.getWidth() + PADDING,VIEWPORT_HEIGHT - imageSize - MARGINS);

        gameInfo.setSize(image.getWidth(), VIEWPORT_HEIGHT - (image.getHeight() + PADDING + MARGINS));
        gameInfo.setPosition(image.getX(), subGame.getY());

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
