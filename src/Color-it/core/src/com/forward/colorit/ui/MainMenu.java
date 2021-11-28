package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

/**
 * Окно главного меню
 */
class MainMenu extends Window {

    private final TextButton playButton = new TextButton("Играть!", Core.core().getUi(), Core.TEXTBUTTON_STYLE_YELLOW);
    private final TextButton settingsButton = new TextButton("Настройки", Core.core().getUi());
    private final TextButton aboutButton = new TextButton("О разработчиках", Core.core().getUi());
    private final TextButton exitButton = new TextButton("Выход", Core.core().getUi());

    MainMenu() {
        super("", Core.core().getUi());
        setMovable(false);
        initButton(playButton, new PlayClickListener());
        initButton(settingsButton, new SettingsClickListener());
        initButton(aboutButton, new AboutClickListener());
        initButton(exitButton, new ExitClickListener());
        pad(Core.UI_PADDING_LARGE);
        pack();
    }

    /**
     * Размещает кнопки в таблице и назначает им слушателя.
     * @param button
     * @param listener
     */
    private void initButton(TextButton button, ClickListener listener){
        row();
        add(button).padBottom(Core.UI_PADDING);
        button.addListener(listener);
        button.addListener(SoundClickListener.getInstance());
    }

    /**
     * Класс слушателя для кнопки выхда.
     * При нажатии на кнопку, происходит выход из приложения.
     */
    private class ExitClickListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    }

    /**
     * Класс слушателя для кнопки "О разработчиках".
     * При нажатии открывает меню с информацией о проекте.
     */
    private class AboutClickListener extends ClickListener{
        // TODO: 27.11.2021
    }

    /**
     * Класс слушателя для кнопки "Настройки".
     * При нажатии открывает меню настроек.
     */
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

    /**
     * Класс слушателя для кнопки "Играть".
     * При нажатии открывает меню выбора уровня.
     */
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
