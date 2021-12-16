package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

import java.nio.charset.StandardCharsets;

import static com.forward.colorit.tool.TableBlockUtils.initLabelBlock;

public class CreditsMenu extends Window {

    private final Table table = new Table();
    private final MainMenu mainMenu;

    public CreditsMenu(MainMenu mainMenu) {
        super("", Core.core().getUi());
        this.mainMenu = mainMenu;
        setMovable(false);

        initBlock1();
        initLabelBlock(table, "Разработчики:");
        initLabelBlock(table, Gdx.files.internal("credits/developers.txt").readString(StandardCharsets.UTF_8.toString()));
        initLabelBlock(table, Gdx.files.internal("credits/licence.txt").readString(StandardCharsets.UTF_8.toString()));
        initLabelBlock(table, Gdx.files.internal("credits/assets_1.txt").readString(StandardCharsets.UTF_8.toString()));
        initLabelBlock(table, Gdx.files.internal("credits/assets_2.txt").readString(StandardCharsets.UTF_8.toString()));
        initLabelBlock(table, Gdx.files.internal("credits/framework.txt").readString(StandardCharsets.UTF_8.toString()));

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
