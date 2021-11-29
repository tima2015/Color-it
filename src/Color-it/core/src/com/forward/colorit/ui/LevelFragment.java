package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.ColoringGameScreen;
import com.forward.colorit.coloring.ColoringLevelData;
import com.forward.colorit.lines.LinesSubGame;
import com.forward.colorit.tool.StartGameType;
import com.forward.colorit.ui.action.StageReplaceAction;

public class LevelFragment extends Table {

    private final ColoringLevelData data;
    private final StartGameType type;
    private final TextureRegion thumbnail;
    private final Texture img;

    private final Image image;


    public LevelFragment(String level, StartGameType type, MainMenu menu) {
        this.data = new Json().fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/" + level + ".json").readString());
        this.type = type;
        boolean completed = Core.getProgressData().isLevelComplete(level);
        thumbnail = Core.core().getManager().get("coloring/thumbnails.txt", TextureAtlas.class)
                .findRegion(completed ? data.getDone_thumbnail() : data.getImg_thumbnail());
        img = Core.core().getManager().get(completed ? data.getDone() : data.getImg());

        add(image = new Image(thumbnail)).size(thumbnail.getRegionWidth(), thumbnail.getRegionHeight()).colspan(2);
        row();

        ImageButton playButton = new ImageButton(Core.core().getUi(), Core.IMAGEBUTTON_PLAY);
        if (!data.getPrev().equals("") && !Core.getProgressData().isLevelComplete(data.getPrev()))
            playButton.setDisabled(true);
        else
            playButton.addListener(new LevelFragmentWindowClickListener(menu));
        add(new Label(data.getName(), Core.core().getUi())).expandX();
        add(playButton);
    }

    private class LevelFragmentWindowClickListener extends ClickListener {

        private final MainMenu menu;

        private LevelFragmentWindowClickListener(MainMenu menu) {
            this.menu = menu;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            getParent().setVisible(false);
            getStage().getActors().removeValue(getParent(), true);
            menu.setVisible(true);
            Core.core().getBackgroundStage().addAction(new StageReplaceAction(Core.core().getStageScreen(), new ColoringGameScreen(new LinesSubGame(), data), .75f));
        }
    }


}
