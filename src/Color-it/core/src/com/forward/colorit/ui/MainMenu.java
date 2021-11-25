package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

class MainMenu extends Window {

    private static final int PLAY_BUTTON_HEIGHT = 75;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 200;
    private static final int SPACE = 24;

    private final TextButton playButton = new SoundTextButton("Играть!", Core.core().getUi(), Core.TEXTBUTTON_STYLE_YELLOW);
    private final TextButton settingsButton = new SoundTextButton("Настройки", Core.core().getUi());
    private final TextButton aboutButton = new SoundTextButton("О разработчиках", Core.core().getUi());
    private final TextButton exitButton = new SoundTextButton("Выход", Core.core().getUi());

    MainMenu() {
        super("", Core.core().getUi());
        setMovable(false);
        //getTitleLabel().setHeight(BUTTON_HEIGHT);
        initButton(playButton, PLAY_BUTTON_HEIGHT, new PlayClickListener());
        initButton(settingsButton, BUTTON_HEIGHT, new SettingsClickListener());
        initButton(aboutButton, BUTTON_HEIGHT, new AboutClickListener());
        initButton(exitButton, BUTTON_HEIGHT, new ExitClickListener());
        pad(SPACE);
        pack();
    }

    private void initButton(TextButton button, float height, ClickListener listener){
        button.setSize(BUTTON_WIDTH, height);
        button.addListener(listener);
        row();
        Cell<TextButton> cell = add(button);
        cell.height(height);
        cell.width(BUTTON_WIDTH);
        cell.pad(SPACE*.25f);
    }

    private class ExitClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    }

    private class AboutClickListener extends ClickListener{

    }

    private class SettingsClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            MainMenu.this.setVisible(false);
            SettingsMenu settingsMenu = new SettingsMenu(MainMenu.this);
            Stage stage = getStage();
            stage.addActor(settingsMenu);
            settingsMenu.setPosition((stage.getWidth() - settingsMenu.getWidth())*.5f, (stage.getHeight() - settingsMenu.getHeight())*.5f);
        }
    }

    private class PlayClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            MainMenu.this.setVisible(false);
            PlayMenu playMenu = new PlayMenu(MainMenu.this);
            Stage stage = getStage();
            stage.addActor(playMenu);
            playMenu.setPosition((stage.getWidth() - playMenu.getWidth())*.5f, (stage.getHeight() - playMenu.getHeight())*.5f);
        }
    }
}
