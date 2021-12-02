package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;
import com.forward.colorit.tool.MusicPlayer;
import com.forward.colorit.ui.action.CloudFlyAction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Экран меню.
 */
public class MenuScreen extends StageScreenAdapter {

    /**
     * Ширина окна просмотра для экрана меню
     */
    public static final int VIEWPORT_WIDTH = 1080;

    /**
     * Высота окна просмотра для экрана меню
     */
    public static final int VIEWPORT_HEIGHT = 1920;

    private static final float cloudSpawnChance = 0.1f;
    private final ArrayList<CloudFlyAction> clouds = new ArrayList<>();

    private final MainMenu mainMenu;

    public MenuScreen(){
        super(new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)), false);
        getStage().setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        mainMenu = new MainMenu();
        getStage().addActor(mainMenu);
        mainMenu.setPosition((getStage().getWidth() - mainMenu.getWidth())*.5f, (getStage().getHeight() - mainMenu.getHeight())*.5f);
        addMusicsToPlayer();
    }

    private void addMusicsToPlayer(){
        AssetManager manager = Core.core().getManager();
        MusicPlayer player = getMusicPlayer();
        player.addMusic(manager.get("music/menu/AcousticGuitar1.mp3"));
        player.addMusic(manager.get("music/menu/BridesBallad.mp3"));
        player.addMusic(manager.get("music/menu/CryinInMyBeer.mp3"));
        player.addMusic(manager.get("music/menu/GreenLeaves.mp3"));
        player.addMusic(manager.get("music/menu/HappyStrummin.mp3"));
        player.addMusic(manager.get("music/menu/MountainSun.mp3"));
        player.shuffle();
    }

    @Override
    public void show() {
        Core.core().setBackground(Core.core().getManager().get("background/backgroundColorForest.png", Texture.class));
        Gdx.input.setInputProcessor(getStage());

        getMusicPlayer().setVolume(Core.getSettings().getMusicVolume());
        getMusicPlayer().start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateClouds();
        getStage().getViewport().apply();
        getStage().act(delta);
        getStage().draw();
    }

    @Override
    public void hide() {
        super.hide();
        getMusicPlayer().stop();
    }

    private void updateClouds(){
        if (MathUtils.random(0f, 1f) < cloudSpawnChance/Math.pow(10, clouds.size())){
            CloudFlyAction action = new CloudFlyAction(Core.core().getBackgroundStage());
            clouds.add(action);
            Core.core().getBackgroundStage().addAction(action);
        }
        clouds.removeIf(CloudFlyAction::isFinished);
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
