package com.forward.colorit.coloring;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;
import com.forward.colorit.SubGameGroup;
import com.forward.colorit.ui.SoundTextButton;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ColoringGameScreen extends ScreenAdapter {

    private static final int VIEWPORT_WIDTH = 1000;
    private static final int VIEWPORT_HEIGHT = 750;
    private static final int MARGINS = 24;
    private static final int PADDING = 12;

    private Stage stage;
    private SubGameGroup subGame;
    private ColoringLevelData data;
    private ColoringImage image;
    private Window gameInfo;
    private final Hashtable<Color, Integer> uncoloredFragmentsCounts = new Hashtable<>();
    private final ArrayList<Label> uncoloredFragmentsCountLabels = new ArrayList<>();

    public ColoringGameScreen(SubGameGroup subGame, ColoringLevelData data) {
        this.subGame = subGame;
        this.data = data;
        this.image = new ColoringImage(data.getImg());
        stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        gameInfo = new Window("", Core.core().getUi());
        for (ColoringMap map : data.getMap()) {
            Color key = Color.valueOf(map.getColor());
            uncoloredFragmentsCounts.put(key, uncoloredFragmentsCounts.getOrDefault(key, 0) + 1);
        }
        Enumeration<Color> keys = uncoloredFragmentsCounts.keys();
        while (keys.hasMoreElements()) {
            Color key = keys.nextElement();
            Label label = new Label(getLocalizedColorName(key) + ": " + uncoloredFragmentsCounts.get(key), Core.core().getUi());
            uncoloredFragmentsCountLabels.add(label);
            label.setUserObject(key);
        }
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
    }

    @Override
    public void show() {

        initSubGame();
        initImage();
        initSubGameInfoActor();
        initGameInfo();

// TODO: 26.11.2021 разбить на родметоды
        // TODO: 26.11.2021 заполнить панель информации
        Gdx.input.setInputProcessor(stage);
    }

    private void initSubGame() {
        stage.addActor(subGame);
        float subGameSize = VIEWPORT_HEIGHT - 2 * MARGINS;
        subGame.setSize(subGameSize, subGameSize);
        subGame.setPosition(MARGINS, MARGINS);
        subGame.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ColoringEvent) {
                    ColoringEvent cEvent = (ColoringEvent) event;
                    for (int i = 0; i < data.getMap().length; i++) {
                        if (data.getMap()[i] != null && Color.valueOf(data.getMap()[i].getColor()).equals(cEvent.color)) {
                            image.color(data.getMap()[i]);
                            data.getMap()[i] = null;
                            uncoloredFragmentsCounts.put(cEvent.color, uncoloredFragmentsCounts.getOrDefault(cEvent.color, 0) - 1);
                            updateUncoloredFragmentsCountLabels();
                            return true;
                        }
                    }
                }
                if (event instanceof GameEndEvent) {
                    // TODO: 25.11.2021
                }
                return false;
            }
        });
    }

    private void initImage() {
        stage.addActor(image);
        float imageSize = VIEWPORT_WIDTH - (subGame.getX() + subGame.getWidth() + PADDING + MARGINS);
        image.setSize(imageSize, imageSize);
        image.setPosition(subGame.getX() + subGame.getWidth() + PADDING, VIEWPORT_HEIGHT - imageSize - MARGINS);
    }

    private void initSubGameInfoActor(){
        Actor infoActor = subGame.getSubGameInfoActor();
        if (infoActor == null) return;
        stage.addActor(infoActor);
        infoActor.setSize(image.getWidth(),  image.getHeight()*.5f);
        infoActor.setPosition(image.getX(), image.getY() - infoActor.getHeight() - PADDING);
    }

    private void initGameInfo() {
        stage.addActor(gameInfo);

        gameInfo.setPosition(image.getX(), subGame.getY());
        gameInfo.pad(PADDING);
        gameInfo.add(new Label("Осталось закрасить:", Core.core().getUi())).padBottom(PADDING);
        for (Label label : uncoloredFragmentsCountLabels) {
            gameInfo.row();
            gameInfo.add(label).padBottom(PADDING);
        }
        gameInfo.row();
        gameInfo.add(new SoundTextButton("Пауза", Core.core().getUi(), Core.TEXTBUTTON_STYLE_YELLOW)).expand().bottom();
        gameInfo.pack();
        gameInfo.setSize(image.getWidth(), (subGame.getSubGameInfoActor() == null ? image.getY() : subGame.getSubGameInfoActor().getY()) - MARGINS - PADDING);

    }

    void updateUncoloredFragmentsCountLabels(){
        for (Label label : uncoloredFragmentsCountLabels) {
            label.setText(getLocalizedColorName((Color) label.getUserObject()) + ": "
                    + uncoloredFragmentsCounts.getOrDefault((Color)label.getUserObject(), 0));
        }
    }

    private String getLocalizedColorName(Color color){
        if (color.equals(Color.BLUE))
            return "Голубой";
        else if (color.equals(Color.RED))
            return "Красный";
        else if (color.equals(Color.YELLOW))
            return "Желтый";
        else if (color.equals(Color.GREEN))
            return "Зелёный";
        // TODO: 26.11.2021
        return "null";
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
