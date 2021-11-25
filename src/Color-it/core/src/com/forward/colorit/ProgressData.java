package com.forward.colorit;

import com.badlogic.gdx.Gdx;

public class ProgressData {

    private static final String progressPreferencesName = "save";

    public static final String LEVEL_ANKHA = "ankha";
    public static final String LEVEL_BUTTERFLY = "butterfly";
    public static final String LEVEL_CAKE = "cake";
    public static final String LEVEL_FISH = "fish";
    public static final String LEVEL_FLOWER = "flower";
    public static final String LEVEL_KITTY = "kitty";
    public static final String LEVEL_RAT = "rat";
    public static final String LEVEL_REI = "rei";
    public static final String LEVEL_SPRUCE = "spruce";

    public boolean isLevelComplete(String levelName){
        return Gdx.app.getPreferences(progressPreferencesName).getBoolean(levelName, false);
    }

    public void setLevelComplete(String levelName){
        Gdx.app.getPreferences(progressPreferencesName).putBoolean(levelName, true).flush();
    }

    //todo
    public void resetSave(){
        Gdx.app.getPreferences(progressPreferencesName).clear();
    }
}
