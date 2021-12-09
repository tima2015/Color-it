package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CreditsMenu extends Window {

    private final Table table = new Table();
    private final MainMenu mainMenu;

    public CreditsMenu(MainMenu mainMenu) {
        super("", Core.core().getUi());
        this.mainMenu = mainMenu;
        setMovable(false);

        initBlock1();
        initBlock2();
        initBlock3();
        initBlock4();
        initBlock5();
        initBlock6();
        initBlock7();

        table.pad(Core.UI_PADDING);
        table.pack();
        ScrollPane pane = new ScrollPane(table);
        pane.setForceScroll(false, true);
        add(pane);
        initBack();
        pack();
    }

    private void initBlock1(){
        Label label = new Label("Колор-ит", Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(label).expand().center().pad(Core.UI_PADDING_LARGE);
    }

    private void initBlock2(){
        table.row();
        Label developers = new Label("Разработчики:", Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(developers).expand().left();
    }

    private void initBlock3(){
        table.row();
        Label developers = new Label(Gdx.files.internal("credits/developers.txt").readString(StandardCharsets.UTF_8.toString()), Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(developers).expand().left();
    }

    private void initBlock4(){
        table.row();
        Label licence = new Label(Gdx.files.internal("credits/licence.txt").readString(StandardCharsets.UTF_8.toString()), Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(licence).expand().left();
    }

    private void initBlock5(){
        table.row();
        Label label = new Label(Gdx.files.internal("credits/assets_1.txt").readString(StandardCharsets.UTF_8.toString()), Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(label).expand().left();
    }

    private void initBlock6(){
        table.row();
        Label label = new Label(Gdx.files.internal("credits/assets_2.txt").readString(StandardCharsets.UTF_8.toString()), Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(label).expand().left();
    }

    private void initBlock7(){
        table.row();
        Label label = new Label(Gdx.files.internal("credits/framework.txt").readString(StandardCharsets.UTF_8.toString()), Core.core().getUi(), Core.LABEL_STYLE_LARGE);
        table.add(label).expand().left();
    }

    private void initBack(){
        row();
        TextButton button = new TextButton("Назад", Core.core().getUi());
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CreditsMenu.this.setVisible(false);
                getStage().getActors().removeValue(CreditsMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        button.addListener(SoundClickListener.getInstance());
        add(button).left().pad(Core.UI_PADDING);
    }
}
