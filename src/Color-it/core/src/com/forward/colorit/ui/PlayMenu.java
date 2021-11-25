package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.forward.colorit.Core;
import com.forward.colorit.ProgressData;
import com.forward.colorit.coloring.ColoringGameScreen;
import com.forward.colorit.coloring.ColoringLevelData;
import com.forward.colorit.lines.LinesSubGame;

public class PlayMenu extends Table {
    private final MainMenu mainMenu;

    public PlayMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initTitles();
        init1ButtonRow();
        initGoBack();
    }

    private void initTitles(){
        add(new Label("Линии", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).expand();
        add(new Label("Три в ряд", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).expand();
        add(new Label("Змейка", Core.core().getUi(), Core.LABEL_STYLE_LARGE)).expand();
    }

    private void init1ButtonRow(){
        row();
        Json json = new Json();
        final ColoringLevelData ankhaData = json.fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/ankha.json").readString());
        ImageButton.ImageButtonStyle style =
                new ImageButton.ImageButtonStyle(Core.core().getUi().get(ImageButton.ImageButtonStyle.class));
        TextureAtlas thumbnails = Core.core().getManager().get("coloring/thumbnails.txt", TextureAtlas.class);
        style.imageUp = new TextureRegionDrawable(thumbnails.findRegion(
                Core.getProgressData().isLevelComplete(ProgressData.LEVEL_ANKHA) ? "ankha_done_thumbnail" : "ankha_thumbnail"));
        ImageButton ankha = new ImageButton(style);
        ankha.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayMenu.this.setVisible(false);
                getStage().getActors().removeValue(PlayMenu.this, true);
                mainMenu.setVisible(true);
                Core.core().setScreen(new ColoringGameScreen(new LinesSubGame(), ankhaData));
            }
        });
        add(ankha);

        //todo ColoringLevelData data = json.fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/spruce.json").readString());
        style = new ImageButton.ImageButtonStyle(style);
        style.imageUp = new TextureRegionDrawable(thumbnails.findRegion(
                Core.getProgressData().isLevelComplete(ProgressData.LEVEL_SPRUCE) ? "spruce_done_thumbnail" : "spruce_thumbnail"));
        add(new ImageButton(style));

        //todo ColoringLevelData data = json.fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/rei.json").readString());
        style = new ImageButton.ImageButtonStyle(style);
        style.imageUp = new TextureRegionDrawable(thumbnails.findRegion(
                Core.getProgressData().isLevelComplete(ProgressData.LEVEL_REI) ? "rei_done_thumbnail" : "rei_thumbnail"));
        add(new ImageButton(style));
    }

    private void initGoBack(){
        row();
        TextButton button = new SoundTextButton("Назад", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayMenu.this.setVisible(false);
                getStage().getActors().removeValue(PlayMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        add(button);
    }


}
