package com.forward.colorit.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;
import com.forward.colorit.tool.StartGameType;

public class PlayMenu extends Table {
    private final MainMenu mainMenu;

    public PlayMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initTitles();
        initLevelFragments();
        initGoBack();
        pack();
    }

    private void initTitles() {
        add(new Label("Линии", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).pad(Core.UI_PADDING).expand();
        add(new Label("Три в ряд", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).pad(Core.UI_PADDING).expand();
        add(new Label("Змейка", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).pad(Core.UI_PADDING).expand();
    }

    private void initLevelFragments() {
        row();

        Table levelFragmentsTable = new Table();

        float height = levelFragmentsTable.add(new LevelFragment("00", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING).getPrefHeight();
        levelFragmentsTable.add(new LevelFragment("00", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.add(new LevelFragment("20", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);// FIXME: 29.11.2021

        levelFragmentsTable.row();
        levelFragmentsTable.add(new LevelFragment("01", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.add(new LevelFragment("01", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.add(new LevelFragment("01", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);

        levelFragmentsTable.row();
        levelFragmentsTable.add(new LevelFragment("02", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.add(new LevelFragment("02", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.add(new LevelFragment("02", StartGameType.LINES, mainMenu)).pad(Core.UI_PADDING);
        levelFragmentsTable.pack();

        ScrollPane pane = new ScrollPane(levelFragmentsTable, Core.core().getUi());
        add(pane).colspan(3).expand().size(levelFragmentsTable.getWidth(), height*1.5f);
        pane.setSize(200, 300);
    }

    private void initGoBack() {
        row();
        TextButton button = new TextButton("Назад", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayMenu.this.setVisible(false);
                getStage().getActors().removeValue(PlayMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        button.addListener(SoundClickListener.getInstance());
        add(button);
    }


}
