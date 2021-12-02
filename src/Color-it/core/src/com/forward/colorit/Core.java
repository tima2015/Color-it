package com.forward.colorit;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forward.colorit.ui.MenuScreen;
import com.forward.colorit.ui.StageScreenAdapter;
import com.forward.colorit.ui.action.StageReplaceAction;

public class Core extends Game {

    private static final String TAG = "Core";

    //Константы стилей
    public static final String TEXTBUTTON_STYLE_YELLOW = "yellow";
    public static final String TEXTBUTTON_STYLE_GREEN = "green";
    public static final String TEXTBUTTON_STYLE_RED = "red";
    public static final String LABEL_STYLE_LARGE = "large";
    public static final String LABEL_STYLE_SMALL = "small";
    public static final String WINDOW_STYLE_PAUSE = "pause";
    public static final String IMAGEBUTTON_PLAY = "play";
    public static final String MAIN_MENU_BUTTON_PLAY = "play";
    public static final String MAIN_MENU_BUTTON_SETTINGS = "settings";
    public static final String MAIN_MENU_BUTTON_CREDITS = "credits";
    public static final String MAIN_MENU_BUTTON_EXIT = "exit";


    //Константы данных для формирования интерфейса
    public static final float UI_PADDING = 16;
    public static final float UI_PADDING_LARGE = UI_PADDING * 4;


    /**
     * @return Возвращает объект класса Core текущей игры .
     */
    public static Core core() {
        return (Core) Gdx.app.getApplicationListener();
    }

    private static Settings settings;

    /**
     * @return Настройки игры.
     */
    public static Settings getSettings() {
        return settings;
    }

    public static ProgressData progressData;

    /**
     * @return Данные о текущем прогрессе игрока.
     */
    public static ProgressData getProgressData() {
        return progressData;
    }

    public Core() {
    }

    private TextureAtlas textures;
    private Skin ui;
    private MenuScreen menuScreen;
    private AssetManager manager;

    private Cursor cursor;
    private Pixmap pixmapCursor;

    private Stage backgroundStage;
    private Texture defBackground;
    private Image backgroundImage;

    @Override
    public void create() {
        Gdx.app.debug(TAG, "create() called");
        manager = new AssetManager();
        backgroundStage = new Stage(new ScreenViewport());
        defBackground = new Texture("background/backgroundForest.png");
        backgroundImage = new Image(defBackground);
        backgroundStage.addActor(backgroundImage);
        initCursor();
        settings = new Settings();
        setScreen(LoadingScreen.getInstance());
    }

    /**
     * Инициализация ядра.
     * Должно быть вызвано после окончания загрузки основных зависимостей.
     * Метод предназначен для LoadingScreen.
     */
    void initCore() {
        Gdx.app.debug(TAG, "initCore() called");
        textures = manager.get("textures.atlas", TextureAtlas.class);
        ui = new Skin(Gdx.files.internal("ui.json"));
        menuScreen = new MenuScreen();
        progressData = new ProgressData();
    }

    /**
     * Инициализирует игровой курсор.
     */
    private void initCursor() {
        pixmapCursor = new Pixmap(Gdx.files.internal("brush.png"));
        cursor = Gdx.graphics.newCursor(pixmapCursor, 0, 0);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        backgroundStage.getViewport().apply();
        backgroundStage.act(Gdx.graphics.getDeltaTime());
        backgroundStage.draw();
        super.render();
        switchDebug();
    }

    private void switchDebug() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            Gdx.app.setLogLevel(Gdx.app.getLogLevel() == Application.LOG_DEBUG ? Application.LOG_ERROR : Application.LOG_DEBUG);
            backgroundStage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug(TAG, "resize() called with: width = [" + width + "], height = [" + height + "]");
        super.resize(width, height);
        backgroundStage.getViewport().update(width, height);
        int size = Math.max(backgroundStage.getViewport().getScreenWidth(),
                backgroundStage.getViewport().getScreenHeight());
        backgroundImage.setSize(size, size);
    }

    @Override
    public void dispose() {
        Gdx.app.debug(TAG, "dispose() called");
        super.dispose();
        defBackground.dispose();
        backgroundStage.dispose();
        cursor.dispose();
        manager.dispose();
        menuScreen.dispose();
        pixmapCursor.dispose();
        LoadingScreen.getInstance().dispose();
    }

    /**
     * @return Основные текстуры миниигр.
     */
    public TextureAtlas getTextures() {
        return textures;
    }

    /**
     * @param background - фоновое изображение.
     */
    public void setBackground(Texture background) {
        backgroundImage.setDrawable(new TextureRegionDrawable(background));
    }

    /**
     * @return Фоновую сцену.
     */
    public Stage getBackgroundStage() {
        return backgroundStage;
    }

    /**
     * @return Текущий экран. Если класс экрана не относится к StageScreenAdapter, вернёт null.
     */
    public StageScreenAdapter getStageScreen() {
        Screen screen = super.getScreen();
        if (screen instanceof StageScreenAdapter)
            return (StageScreenAdapter) screen;
        return null;
    }

    /**
     * @return Данные для визуализации пользовательского интерфейса.
     */
    public Skin getUi() {
        return ui;
    }

    /**
     * @return Внутриигровой курсор.
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * @return Менеджер зависимостей. Содержащий в себе все основные зависимости игры.
     */
    public AssetManager getManager() {
        return manager;
    }

    /**
     * Переводит игру в главное меню из какого либо другого экрана.
     */
    public void setStateToMenuScreen() {
        Gdx.app.debug(TAG, "setStateToMenuScreen() called");
        menuScreen.getMainMenu().setVisible(true);
        Screen old = getStageScreen();
        if (getStageScreen() != null) {
            backgroundStage.addAction(new StageReplaceAction(((StageScreenAdapter) old), menuScreen, 0.75f));
        } else {
            old = getScreen();
            setScreen(menuScreen);
            if (old == null || old.equals(menuScreen) || old.equals(LoadingScreen.getInstance())) return;
            old.dispose();
        }
    }
}
