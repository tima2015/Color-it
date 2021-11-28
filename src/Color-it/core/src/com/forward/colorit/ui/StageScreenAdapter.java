package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.forward.colorit.tool.MusicPlayer;

/**
 * Класс экрана, обязательно содержащий экземпляр Stage
 * и флаг для определния, стоит ли удалять экземпляр экрана при его замене
 * Так же содержит объект музыкального плеера.
 */
public class StageScreenAdapter extends ScreenAdapter {

    private final Stage stage;
    private final boolean autoDispose;
    private final MusicPlayer musicPlayer = new MusicPlayer();

    public StageScreenAdapter(Stage stage, boolean autoDispose) {
        this.stage = stage;
        this.autoDispose = autoDispose;
    }

    /**
     * @return Возвращает сцену экрана
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @return требуется удаление при замете данного экрана другим.
     */
    public boolean isAutoDispose() {
        return autoDispose;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    @Override
    public void render(float delta) {
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
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
