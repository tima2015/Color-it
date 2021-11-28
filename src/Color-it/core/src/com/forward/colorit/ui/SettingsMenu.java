package com.forward.colorit.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

/**
 * Меню настроек.
 * Позволяет пользователю редактировать настройки.
 */
public class SettingsMenu extends Window {

    private static final String TAG = "SettingsMenu";

    private final MainMenu mainMenu;

    private final Slider musicVolume = new Slider(0f,1f,0.01f, false,Core.core().getUi());
    private final Slider soundVolume = new Slider(0f, 1f, 0.01f, false, Core.core().getUi());
    private final CheckBox fullscreen = new CheckBox("",Core.core().getUi());
    private final CheckBox systemCursor = new CheckBox("", Core.core().getUi());

    public SettingsMenu(MainMenu mainMenu) {
        super("", Core.core().getUi());
        this.mainMenu = mainMenu;
        setMovable(false);
        getTitleTable().center();
        initMusicVolume();
        initSoundVolume();
        if (Gdx.app.getType() != Application.ApplicationType.Android && Gdx.app.getType() != Application.ApplicationType.iOS) {
            initFullscreenCheckBox();
            initSystemCursorCheckBox();
        }
        initResetProgressButton();
        initCancel();
        initSave();
        pad(Core.UI_PADDING_LARGE);
        pack();
    }

    private void initMusicVolume(){
        row();
        add(new Label("Громкость музыки", Core.core().getUi())).padBottom(Core.UI_PADDING);
        add(musicVolume).padBottom(Core.UI_PADDING);
        musicVolume.setValue(Core.getSettings().getMusicVolume());
        musicVolume.addListener(SoundClickListener.getInstance());
    }

    private void initSoundVolume(){
        row();
        add(new Label("Громкость звуков", Core.core().getUi())).padBottom(Core.UI_PADDING);
        add(soundVolume).padBottom(Core.UI_PADDING);
        soundVolume.setValue(Core.getSettings().getSoundVolume());
        soundVolume.addListener(SoundClickListener.getInstance());
    }

    private void initFullscreenCheckBox(){
        row();
        add(new Label("Полноэкранный режим", Core.core().getUi())).padBottom(Core.UI_PADDING);
        add(fullscreen).padBottom(Core.UI_PADDING);
        fullscreen.setChecked(Core.getSettings().isFullscreen());
        fullscreen.addListener(SoundClickListener.getInstance());
    }

    private void initSystemCursorCheckBox(){
        row();
        add(new Label("Системный курсор", Core.core().getUi())).padBottom(Core.UI_PADDING);
        add(systemCursor).padBottom(Core.UI_PADDING);
        systemCursor.setChecked(Core.getSettings().isSystemCursor());
        systemCursor.addListener(SoundClickListener.getInstance());
    }

    private void initResetProgressButton(){
        row();
        add(new Label("Сброс прогресса", Core.core().getUi())).padBottom(Core.UI_PADDING);
        TextButton b = new TextButton("Сбросить прогресс!", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DialogWindow dialogWindow = new DialogWindow("Вы уверены?", Core.core().getUi(), Core.WINDOW_STYLE_PAUSE, () -> {
                    Core.getProgressData().resetSave();
                    Gdx.app.log(TAG, "Progress reset!");
                    MessageWindow window = new MessageWindow("", Core.core().getUi(), Core.WINDOW_STYLE_PAUSE, "Прогресс сброшен!");
                    getStage().addActor(window);
                    window.setPosition((getStage().getWidth() - window.getWidth())*.5f, (getStage().getHeight() - window.getHeight())*.5f);
                }, () -> {});
                getStage().addActor(dialogWindow);
                dialogWindow.setPosition((getStage().getWidth() - dialogWindow.getWidth())*.5f, (getStage().getHeight() - dialogWindow.getHeight())*.5f);
            }
        });
        b.addListener(SoundClickListener.getInstance());
        add(b).padBottom(Core.UI_PADDING).center();
    }

    private void initCancel(){
        row();
        TextButton button = new TextButton("Отмена", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsMenu.this.setVisible(false);
                getStage().getActors().removeValue(SettingsMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        button.addListener(SoundClickListener.getInstance());
        add(button).pad(Core.UI_PADDING);
    }

    private void initSave(){
        TextButton button = new TextButton("Сохранить", Core.core().getUi(), Core.TEXTBUTTON_STYLE_GREEN);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsMenu.this.setVisible(false);
                getStage().getActors().removeValue(SettingsMenu.this, true);
                mainMenu.setVisible(true);
                Core.getSettings().savePreference(soundVolume.getValue(),
                        musicVolume.getValue(),
                        fullscreen.isChecked(),
                        systemCursor.isChecked());
            }
        });
        button.addListener(SoundClickListener.getInstance());
        add(button).pad(Core.UI_PADDING);
    }





}
