package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.ColoringLevelData;

public class LevelFragment extends Table {

    private final ColoringLevelData data;
    private final TextureRegion thumbnail;
    private final Texture img;

    private final Image image;


    public LevelFragment(ColoringLevelData data, ClickListener playButtonListener) {
        this.data = data;
        boolean completed = Core.getProgressData().isLevelComplete(data.getId());
        thumbnail = Core.core().getManager().get("coloring/thumbnails.txt", TextureAtlas.class)
                .findRegion(completed ? data.getDone_thumbnail() : data.getImg_thumbnail());
        img = Core.core().getManager().get(completed ? data.getDone() : data.getImg());

        add(image = new Image(thumbnail)).size(100, 100).colspan(2);
        row();

        ImageButton playButton = new ImageButton(Core.core().getUi(), Core.IMAGEBUTTON_PLAY);
        if (!data.getPrev().equals("") && !Core.getProgressData().isLevelComplete(data.getPrev()))
            playButton.setDisabled(true);
        else
            playButton.addListener(playButtonListener);
        add(new Label(data.getName(), Core.core().getUi())).expandX();
        add(playButton);
    }




}
