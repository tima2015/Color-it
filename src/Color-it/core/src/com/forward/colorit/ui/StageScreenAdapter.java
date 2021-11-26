package com.forward.colorit.ui;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Класс экрана, обязательно содержащий экземпляр Stage
 * и флаг для определния, стоит ли удалять экземпляр экрана при его замене
 */
public class StageScreenAdapter extends ScreenAdapter {

    private final Stage stage;
    private final boolean autoDispose;

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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
