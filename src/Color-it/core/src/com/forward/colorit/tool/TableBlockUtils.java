package com.forward.colorit.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.forward.colorit.Core;

/**
 * Класс инструментов по заполнению таблиц
 */
public class TableBlockUtils {

    private static final String TAG = "TableBlockUtils";

    /**
     * инициализация текстового блока
     * @param text текст блока
     */
    public static void initLabelBlock(Table table, String text) {
        Gdx.app.debug(TAG, "initLabelBlock() called with: text = [" + text + "]");
        table.row();
        Label developers = new Label(text, Core.core().getUi());
        table.add(developers).expand().left();

    }

    /**
     * инициализация блока изображения
     * @param region изображение блока
     */
    public static void initImageBlock(Table table, TextureRegion region){
        Gdx.app.debug(TAG, "initImageBlock() called with: region = [" + region + "]");
        table.row();
        Image image = new Image(region);
        table.add(image).width(600).center();
    }
}
