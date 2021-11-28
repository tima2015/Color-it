package com.forward.colorit.ui;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

public class SoundClickListener extends ClickListener {

    private static final SoundClickListener soundClickListener = new SoundClickListener();

    public static SoundClickListener getInstance() {
        return soundClickListener;
    }

    private final Sound click = Core.core().getManager().get("sound/click1.ogg", Sound.class);
    private final Sound switch1 = Core.core().getManager().get("sound/switch1.ogg", Sound.class);
    private final Sound switch2 = Core.core().getManager().get("sound/switch2.ogg", Sound.class);


    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        Actor actor = event.getListenerActor();
        if (actor instanceof CheckBox){
            if (((CheckBox)actor).isChecked()) switch1.play(Core.getSettings().getSoundVolume());
            else switch2.play(Core.getSettings().getSoundVolume());
        } else if (actor instanceof Button){
            click.play(Core.getSettings().getSoundVolume());
        }
    }
}
