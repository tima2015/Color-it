package com.forward.colorit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Cursor;

/**
 * Класс для персональной настройки параметров игры, пользователем.
 */
public class Settings {
    private static float defaultSoundVolume = 1;
    private static float defaultMusicVolume = 1;
    private static boolean defaultFullscreen = false;
    private static boolean defaultSystemCursor = false;

    private static final Preferences pref = Gdx.app.getPreferences("pref");

    Settings(){
        refreshSettings();
    }

    /**
     * Сохраняет настройки пользователя.
     * @param soundVolume Громкость аудиоэффектов.
     * @param musicVolume Громкость музыки.
     * @param fullscreen Полноэкранный режим.
     * @param systemCursor Использование системного курсора.
     */
    public void savePreference(float soundVolume, float musicVolume, boolean fullscreen, boolean systemCursor){
        pref.putFloat("sound", soundVolume)
                .putFloat("music", musicVolume)
                .putBoolean("fullscreen", fullscreen)
                .putBoolean("system_cursor", systemCursor)
                .flush();
        refreshSettings();
    }

    /**
     * @return Текущая громкость аудиоэффектов.
     */
    public float getSoundVolume() {
        return pref.getFloat("sound", defaultSoundVolume);
    }

    /**
     * @return Текущая громкость музыки.
     */
    public float getMusicVolume() {
        return pref.getFloat("music", defaultMusicVolume);
    }

    /**
     * @return Истина, если следует использовать полноэкранный режим.
     */
    public boolean isFullscreen() {
        return pref.getBoolean("fullscreen", defaultFullscreen);
    }

    /**
     * @return Истина, если стоит использовать системный курсор.
     */
    public boolean isSystemCursor() {
        return pref.getBoolean("system_cursor", defaultSystemCursor);
    }

    /**
     * Обновление состояния игры с учётом настроек.
     */
    public void refreshSettings() {
        if (isSystemCursor())
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        else
            Gdx.graphics.setCursor(Core.core().getCursor());
        if (isFullscreen())
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }
}
