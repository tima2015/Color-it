package com.forward.colorit.coloring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Cursor;
import com.forward.colorit.Core;

public class Settings {
    private float soundVolume;
    private float musicVolume;
    private boolean fullscreen;
    private boolean systemCursor;

    private static float defaultSoundVolume = 1;
    private static float defaultMusicVolume = 1;
    private static boolean defaultFullscreen = false;
    private static boolean defaultSystemCursor = false;

    private static final Preferences pref = Gdx.app.getPreferences("pref");

    public Settings(){
        refreshSettings();
    }

    public void savePreference(float soundVolume, float musicVolume, boolean fullscreen, boolean systemCursor){
        pref.putFloat("sound", soundVolume)
                .putFloat("music", musicVolume)
                .putBoolean("fullscreen", fullscreen)
                .putBoolean("system_cursor", systemCursor)
                .flush();
        refreshSettings();
    }

    public float getSoundVolume() {
        return pref.getFloat("sound", defaultSoundVolume);
    }

    public float getMusicVolume() {
        return pref.getFloat("music", defaultMusicVolume);
    }

    public boolean isFullscreen() {
        return pref.getBoolean("fullscreen", defaultFullscreen);
    }

    public boolean isSystemCursor() {
        return pref.getBoolean("system_cursor", defaultSystemCursor);
    }

    public void refreshSettings() {
        if (isSystemCursor())
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        else
            Gdx.graphics.setCursor(Core.core().getCursor());
        if (isFullscreen())
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }
}
