package com.forward.colorit.ui;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

/**
 * Клавиша с аудиоэффектами
 */
public class SoundTextButton extends TextButton {
    public SoundTextButton(String text, Skin skin) {
        super(text, skin);
        addListener(new SoundTextButtonClickListener());
    }

    public SoundTextButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        addListener(new SoundTextButtonClickListener());
    }

    public SoundTextButton(String text, TextButtonStyle style) {
        super(text, style);
        addListener(new SoundTextButtonClickListener());
    }

    private class SoundTextButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            Core.core().getManager().get("sound/click1.ogg", Sound.class).play(Core.getSettings().getSoundVolume());
        }
    }
}
