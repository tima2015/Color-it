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

    private final Array<TextureAtlas.AtlasRegion> regions = Core.core().getManager().get("instruction/lines.txt", TextureAtlas.class).findRegions("lines");
    private final Table table = new Table();
    private final CheckBox checkBox = new CheckBox("  Больше не показывать", Core.core().getUi());


    public LinesInstructionActor() {
        super("", Core.core().getUi());
        setModal(true);
        setMovable(false);

        initLabelBlock(Gdx.files.internal("instruction/lines_0.txt").readString(StandardCharsets.UTF_8.toString()));
        initBlock2();
        initLabelBlock(Gdx.files.internal("instruction/lines_1.txt").readString(StandardCharsets.UTF_8.toString()));
        initBlock4();
        initLabelBlock(Gdx.files.internal("instruction/lines_2.txt").readString(StandardCharsets.UTF_8.toString()));
        initBlock6();
        initLabelBlock(Gdx.files.internal("instruction/lines_3.txt").readString(StandardCharsets.UTF_8.toString()));
        initBlock8();// FIXME: 09.12.2021 тут куча дублирующегося кода
        initBlock9();

        table.pad(Core.UI_PADDING);
        table.pack();
        ScrollPane pane = new ScrollPane(table);
        pane.setForceScroll(false, true);
        add(pane);
        initOk();
        pack();

    }

    private void initBlock2(){
        table.row();
        Image image = new Image(regions.get(0));
        table.add(image).size(400, 216).center();
        image.setSize(200, 108);
    }

    private void initBlock4(){
        table.row();
        Image image = new Image(regions.get(1));
        table.add(image).expand().center();
    }

    private void initLabelBlock(String text){
        Gdx.app.debug(TAG, "initLabelBlock() called with: text = [" + text + "]");
        table.row();
        Label developers = new Label(text , Core.core().getUi());
        table.add(developers).expand().left();

    }

    private void initBlock6(){
        table.row();
        Image image = new Image(regions.get(2));
        table.add(image).width(600).center();
    }

    private void initBlock8(){
        table.row();
        Image image = new Image(regions.get(3));
        table.add(image).expand().center();
    }

    private void initBlock9(){
        table.row();
        table.add(checkBox).left();
    }

    private void initOk(){
        row();
        TextButton button = new TextButton("Назад", Core.core().getUi());
        button.addListener(new ClickListener(){
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
