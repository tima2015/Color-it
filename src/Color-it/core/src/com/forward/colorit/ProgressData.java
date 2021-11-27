package com.forward.colorit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Класс объект которого позволяет получить информацию о текущем прогрессе игрока.
 */
public class ProgressData {

    public static final String LEVEL_ANKHA = "ankha";
    public static final String LEVEL_BUTTERFLY = "butterfly";
    public static final String LEVEL_CAKE = "cake";
    public static final String LEVEL_FISH = "fish";
    public static final String LEVEL_FLOWER = "flower";
    public static final String LEVEL_KITTY = "kitty";
    public static final String LEVEL_RAT = "rat";
    public static final String LEVEL_REI = "rei";
    public static final String LEVEL_SPRUCE = "spruce";

    private static final Preferences save = Gdx.app.getPreferences("save");

    ProgressData(){}

    /**
     * @param levelName Наименование уровня
     * @return Истина, если уровень был пройден. Иначе, вернёт ложь.
     */
    public boolean isLevelComplete(String levelName){
        return save.getBoolean(levelName, false);
    }

    /**
     * Устанавливает уровень как пройденый.
     * @param levelName Наименование уровня.
     */
    public void setLevelComplete(String levelName){
        save.putBoolean(levelName, true).flush();
    }


    /**
     * Сбрасывает прогресс пользователя
     */
    public void resetSave(){
        save.clear();
        save.flush();
    }
}
