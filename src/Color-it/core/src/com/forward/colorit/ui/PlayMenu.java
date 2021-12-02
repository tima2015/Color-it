package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private final GameTypeActor[] gameTypeActors = new GameTypeActor[3];
    private Cell<GameTypeActor> content;
    private Button backButton;

    private int current = 1;

    public PlayMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initGameTypeActor();

        Button leftButton = new TextButton("Влево", Core.core().getUi());
        Button rightButton = new TextButton("Вправо", Core.core().getUi());
        leftButton.addListener(new LeftButtonClickListener());
        rightButton.addListener(new RightButtonClickListener());

        add(leftButton);
        content = add(gameTypeActors[current]);
        add(rightButton);

        initGoBack();
        content.height(MenuScreen.VIEWPORT_HEIGHT - (Core.UI_PADDING_LARGE*2 + backButton.getHeight()));
        pack();
    }

    private void initGameTypeActor(){
        Json json = new Json();
        gameTypeActors[0] = new GameTypeActor(json.fromJson(SubGame.class,
                Gdx.files.internal("coloring/lines.json")),
                SubGameStarters.LINES);
        gameTypeActors[1] = new GameTypeActor(json.fromJson(SubGame.class,
                Gdx.files.internal("coloring/mt.json")),
                SubGameStarters.MATCH_THREE);
        gameTypeActors[2] = new GameTypeActor(json.fromJson(SubGame.class,
                Gdx.files.internal("coloring/snake.json")),
                SubGameStarters.SNAKE);
    }

    private void replace(){
    }

    private void initGoBack() {
        row();
        backButton = new TextButton("Назад", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayMenu.this.setVisible(false);
                getStage().getActors().removeValue(PlayMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        backButton.addListener(SoundClickListener.getInstance());
        add(backButton);
    }

    private class GameTypeActor extends Table{

        private final Image gameTypeThumbnails;
        private final Table levelsTable = new Table();
        private final ScrollPane pane = new ScrollPane(levelsTable, Core.core().getUi());

        public GameTypeActor(SubGame type, SubGameStarters starter) {
            TextureAtlas t = Core.core().getManager().get("coloring/thumbnails.txt", TextureAtlas.class);
            gameTypeThumbnails = new Image(t.findRegion(type.getThumbnail()));
            for (String level : type.getLevels()) {
                initLevelFragment(levelsTable, level, starter);
                levelsTable.row();
            }
            levelsTable.pack();
            add(gameTypeThumbnails).size(100);
            row();
            add(pane);
            pack();
        }

        private Cell<LevelFragment> initLevelFragment(Table table, String level, SubGameStarters starter){
            ColoringLevelData data = new Json().fromJson(ColoringLevelData.class, Gdx.files.internal("coloring/" + level + ".json").readString());
            LevelFragment fragment = new LevelFragment(data, new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (((Button) event.getListenerActor()).isDisabled())
                        return;
                    setVisible(false);
                    getStage().getActors().removeValue(PlayMenu.this, true);
                    mainMenu.setVisible(true);
                    starter.run(data);
                }
            });
            return table.add(fragment).pad(Core.UI_PADDING);
        }
    }

    private class RightButtonClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (++current == gameTypeActors.length)
                current = 0;
            content.setActor(gameTypeActors[current]);
        }
    }

    private class LeftButtonClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (--current < 0)
                current = gameTypeActors.length - 1;
            content.setActor(gameTypeActors[current]);
        }
    }

}
