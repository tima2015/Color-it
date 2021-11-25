package com.forward.colorit.ui;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

public class SettingsMenu extends Window {

    private static final int SPACE = 24;
    private static final int ROW_HEIGHT = 50;
    private static final int ROW_WIDTH = 200;

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
        getTitleTable().pad(SPACE);
        initMusicVolume();
        initSoundVolume();
        initFullscreenCheckBox();
        initSystemCursorCheckBox();
        initCancel();
        initSave();
        pack();
    }

    private void initMusicVolume(){
        row();
        add(new Label("Громкость музыки", Core.core().getUi()));
        add(musicVolume);
        musicVolume.setValue(Core.getSettings().getMusicVolume());
    }

    private void initSoundVolume(){
        row();
        add(new Label("Громкость звуков", Core.core().getUi()));// TODO: 24.11.2021 убрать повторяющийся код
        add(soundVolume);
        soundVolume.setValue(Core.getSettings().getSoundVolume());
    }

    private void initFullscreenCheckBox(){
        row();
        add(new Label("Полноэкранный режим", Core.core().getUi()));
        add(fullscreen);
        fullscreen.setChecked(Core.getSettings().isFullscreen());
    }

    private void initSystemCursorCheckBox(){
        row();
        add(new Label("Системный курсор", Core.core().getUi()));
        add(systemCursor);
        systemCursor.setChecked(Core.getSettings().isSystemCursor());
    }

    private void initCancel(){
        row();
        TextButton button = new SoundTextButton("Отмена", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsMenu.this.setVisible(false);
                getStage().getActors().removeValue(SettingsMenu.this, true);
                mainMenu.setVisible(true);
            }
        });
        add(button);
    }

    private void initSave(){
        TextButton button = new SoundTextButton("Сохранить", Core.core().getUi(), Core.TEXTBUTTON_STYLE_GREEN);
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
        add(button);
    }





}
