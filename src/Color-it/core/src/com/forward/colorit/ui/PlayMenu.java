package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.ColoringLevelData;
import com.forward.colorit.coloring.SubGame;
import com.forward.colorit.tool.SubGameStarters;

public class PlayMenu extends Table {
    private final MainMenu mainMenu;
    private final SelectSubGameMenu menu;

    private final ColoringLevelData[] data;
    private final SubGameStarters starter;

    public PlayMenu(MainMenu mainMenu, SelectSubGameMenu menu, SubGame subGame) {
        this.mainMenu = mainMenu;
        this.menu = menu;

        Json json = new Json();
        data = new ColoringLevelData[subGame.getLevels().length];
        for (int i = 0; i < data.length; i++)
            data[i] = json.fromJson(ColoringLevelData.class, Gdx.files.internal(subGame.getLevels()[i]));
        starter = SubGameStarters.valueOf(subGame.getStarter());

        add(new Label("Выберите уровень", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).expand().center().pad(Core.UI_PADDING);
        row();
        initLevelPanel();
        row();
        Button backButton = new TextButton("Назад", Core.core().getUi());
        backButton.addListener(new BackButtonClickListener());
        add(backButton).expand().left().pad(Core.UI_PADDING);
        pack();
    }

    private void initLevelPanel(){
        Table subGameTable = new Table();
        for (ColoringLevelData d : data) {
            Actor levelActor = new Image(Core.core().getManager().get("coloring/thumbnails.txt", TextureAtlas.class)
                    .findRegion(Core.getProgressData().isLevelComplete(d.getId()) ? d.getDone_thumbnail() : d.getImg_thumbnail()));
            levelActor.addListener(new LevelActorClickListener());
            levelActor.setUserObject(d);
            subGameTable.add(levelActor).expand().center().pad(Core.UI_PADDING);
        }
        add(subGameTable).expand().center().pad(Core.UI_PADDING);
        subGameTable.pack();
    }

    private class LevelActorClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            starter.run((ColoringLevelData) event.getListenerActor().getUserObject());
            PlayMenu.this.setVisible(false);
            getStage().getActors().removeValue(PlayMenu.this, true);
            getStage().getActors().removeValue(menu, true);
        }
    }

    private class BackButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            menu.setVisible(true);
            PlayMenu.this.setVisible(false);
            getStage().getActors().removeValue(PlayMenu.this, true);
        }
    }

}
