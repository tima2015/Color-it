package com.forward.colorit.lines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.forward.colorit.Core;
import com.forward.colorit.ui.SoundClickListener;

import java.nio.charset.StandardCharsets;

public class LinesInstructionActor extends Window {

    private static final String TAG = "LinesInstructionActor";

    private final Table table = new Table();
    private final CheckBox checkBox = new CheckBox("  Больше не показывать", Core.core().getUi());


    public LinesInstructionActor() {
        super("", Core.core().getUi());
        Gdx.app.debug(TAG, "LinesInstructionActor() called");
        setModal(true);
        setMovable(false);

        Array<TextureAtlas.AtlasRegion> regions = Core.core().getManager()
                .get("instruction/lines.txt", TextureAtlas.class).findRegions("lines");

        initLabelBlock(Gdx.files.internal("instruction/lines_0.txt").readString(StandardCharsets.UTF_8.toString()));
        initImageBlock(regions.get(0));
        initLabelBlock(Gdx.files.internal("instruction/lines_1.txt").readString(StandardCharsets.UTF_8.toString()));
        initImageBlock(regions.get(1));
        initLabelBlock(Gdx.files.internal("instruction/lines_2.txt").readString(StandardCharsets.UTF_8.toString()));
        initImageBlock(regions.get(2));
        initLabelBlock(Gdx.files.internal("instruction/lines_3.txt").readString(StandardCharsets.UTF_8.toString()));
        initImageBlock(regions.get(3));
        initCheckBoxBlock();

        table.pad(Core.UI_PADDING);
        table.pack();
        ScrollPane pane = new ScrollPane(table);
        pane.setForceScroll(false, true);
        add(pane);
        initOk();
        pack();
    }

    /**
     * инициализация текстового блока
     * @param text текст блока
     */
    private void initLabelBlock(String text) {
        Gdx.app.debug(TAG, "initLabelBlock() called with: text = [" + text + "]");
        table.row();
        Label developers = new Label(text, Core.core().getUi());
        table.add(developers).expand().left();

    }

    /**
     * инициализация блока изображения
     * @param region изображение блока
     */
    private void initImageBlock(TextureRegion region){
        Gdx.app.debug(TAG, "initImageBlock() called with: region = [" + region + "]");
        table.row();
        Image image = new Image(region);
        table.add(image).width(600).center();
    }

    /**
     * Инициализация чекбокса "Больше не показывать"
     */
    private void initCheckBoxBlock() {
        table.row();
        table.add(checkBox).left();
    }

    private void initOk() {
        row();
        TextButton button = new TextButton("Назад", Core.core().getUi());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LinesInstructionActor.this.setVisible(false);
                getStage().getActors().removeValue(LinesInstructionActor.this, true);
                Core.getSettings().saveInstructionShowPreference(!checkBox.isChecked());
            }
        });
        button.addListener(SoundClickListener.getInstance());
        add(button).left().pad(Core.UI_PADDING);
    }
}
