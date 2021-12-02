package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

/**
 * Окно главного меню
 */
public class MainMenu extends Group {

    private final Button playButton = new Button(Core.core().getUi(), Core.MAIN_MENU_BUTTON_PLAY);
    private final Button settingsButton = new Button(Core.core().getUi(), Core.MAIN_MENU_BUTTON_SETTINGS);
    private final Button aboutButton = new Button(Core.core().getUi(), Core.MAIN_MENU_BUTTON_CREDITS);
    private final Button exitButton = new Button(Core.core().getUi(), Core.MAIN_MENU_BUTTON_EXIT);
    private final Actor panel = new Image(Core.core().getManager().get("mainmenu_panel.png", Texture.class));

    MainMenu() {
        setSize(panel.getWidth(), panel.getHeight());
        addActor(panel);

        initButton(exitButton, new ExitClickListener(), 488, 189);
        initButton(aboutButton, new AboutClickListener(), 535, 421);
        initButton(settingsButton, new SettingsClickListener(), 445, 636);
        initButton(playButton, new PlayClickListener(), 289, 752);

    }

    /**
     * Размещает кнопки в таблице и назначает им слушателя.
     * @param button
     * @param listener
     */
    private void initButton(Button button, ClickListener listener, float x, float y){
        addActor(button);
        button.addListener(listener);
        button.addListener(SoundClickListener.getInstance());
        button.setPosition(x,y);
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
            SelectSubGameMenu selectSubGameMenu = new SelectSubGameMenu(MainMenu.this);
            Stage stage = getStage();
            stage.addActor(selectSubGameMenu);
            selectSubGameMenu.setPosition((stage.getWidth() - selectSubGameMenu.getWidth())*.5f, (stage.getHeight() - selectSubGameMenu.getHeight())*.5f);
        }
    }
}
