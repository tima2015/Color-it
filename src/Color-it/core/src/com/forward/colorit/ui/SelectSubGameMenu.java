package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.SubGame;

public class SelectSubGameMenu extends Table {

    private final SubGame[] subGames;
    private final MainMenu menu;

    public SelectSubGameMenu(MainMenu menu) {
        this.menu = menu;
        Json json = new Json();
        subGames = new SubGame[] {
                json.fromJson(SubGame.class, Gdx.files.internal("coloring/lines.json")),
                json.fromJson(SubGame.class, Gdx.files.internal("coloring/mt.json")),
                json.fromJson(SubGame.class, Gdx.files.internal("coloring/snake.json"))
        };
        add(new Label("Выберите миниигру", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).expand().center().pad(Core.UI_PADDING);
        row();
        initSubGamePanel();
        row();
        Button backButton = new TextButton("В главное меню", Core.core().getUi());
        backButton.addListener(new BackButtonClickListener());
        add(backButton).expand().left().pad(Core.UI_PADDING);
        pack();
    }

    private void initSubGamePanel(){
        Table subGameTable = new Table();
        for (SubGame sg : subGames) {
            Actor subGameActor =
                    new Image(Core.core().getManager()
                            .get("coloring/thumbnails.txt", TextureAtlas.class).findRegion(sg.getThumbnail()));
            subGameActor.addListener(new SubGameActorClickListener());
            subGameActor.setUserObject(sg);
            subGameTable.add(subGameActor).expand().center().pad(Core.UI_PADDING);
        }
        add(subGameTable).expand().center().pad(Core.UI_PADDING);
        subGameTable.pack();
    }

    private class SubGameActorClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            SelectSubGameMenu.this.setVisible(false);
            PlayMenu pm = new PlayMenu(menu, SelectSubGameMenu.this, (SubGame) event.getListenerActor().getUserObject());
            getStage().addActor(pm);
            pm.setPosition((getStage().getWidth() - pm.getWidth())*.5f, (getStage().getHeight() - pm.getHeight())*.5f);
        }
    }

    private class BackButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            menu.setVisible(true);
            SelectSubGameMenu.this.setVisible(false);
            getStage().getActors().removeValue(SelectSubGameMenu.this, true);
        }
    }
}
