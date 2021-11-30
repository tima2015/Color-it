package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.ColoringGameScreen;
import com.forward.colorit.coloring.ColoringLevelData;
import com.forward.colorit.lines.LinesSubGame;
import com.forward.colorit.tool.RunnableClickListener;
import com.forward.colorit.tool.StartGameType;
import com.forward.colorit.ui.action.StageReplaceAction;

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

        float height = initLevelFragment(levelFragmentsTable, "00", StartGameType.LINES).getPrefHeight();
        initLevelFragment(levelFragmentsTable, "00", StartGameType.LINES);
        initLevelFragment(levelFragmentsTable, "20", StartGameType.LINES);// FIXME: 30.11.2021

        levelFragmentsTable.row();

        initLevelFragment(levelFragmentsTable, "01", StartGameType.LINES);
        initLevelFragment(levelFragmentsTable, "01", StartGameType.LINES);
        initLevelFragment(levelFragmentsTable, "21", StartGameType.LINES);

        levelFragmentsTable.row();
        initLevelFragment(levelFragmentsTable, "02", StartGameType.LINES);
        initLevelFragment(levelFragmentsTable, "02", StartGameType.LINES);
        initLevelFragment(levelFragmentsTable, "02", StartGameType.LINES);

        levelFragmentsTable.pack();

        ScrollPane pane = new ScrollPane(levelFragmentsTable, Core.core().getUi());
        add(pane).colspan(3).expand().size(levelFragmentsTable.getWidth(), height*1.5f);
    }

    private Cell<LevelFragment> initLevelFragment(Table table, String level, StartGameType type){
        ColoringLevelData data = new Json().fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/" + level + ".json").readString());
        LevelFragment fragment = new LevelFragment(data, new RunnableClickListener() {
            @Override
            public void run() {
                setVisible(false);
                getStage().getActors().removeValue(PlayMenu.this, true);
                mainMenu.setVisible(true);
                type.run(data);
            }
        });
        return table.add(fragment).pad(Core.UI_PADDING);
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
