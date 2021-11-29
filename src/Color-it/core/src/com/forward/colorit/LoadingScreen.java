package com.forward.colorit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.forward.colorit.ui.StageScreenAdapter;

/**
 * Класс экрана загрузки. Предотвращает ощущение зависания, при запуске приложения.
 * Назначает зависимости для загрузки.
 * Реализует паттерн Синглтон.
 */
class LoadingScreen extends StageScreenAdapter {

    private static final LoadingScreen loadingScreen = new LoadingScreen();

    /**
     * @return экземпляр экрана загрузки
     */
    static LoadingScreen getInstance() {
        return loadingScreen;
    }

    private LoadingScreen(){
        super(new Stage(Core.core().getBackgroundStage().getViewport()), false);
        load();
    }

    /**
     * Назначение файлов, которые должен будет загрузить менеджер зависимостей
     */
    private void load(){
        AssetManager am = Core.core().getManager();
        am.load("coloring/000.png", Texture.class);
        am.load("coloring/001.png", Texture.class);
        am.load("coloring/010.png", Texture.class);
        am.load("coloring/011.png", Texture.class);
        am.load("coloring/020.png", Texture.class);
        am.load("coloring/021.png", Texture.class);
        am.load("coloring/200.png", Texture.class);
        am.load("coloring/201.png", Texture.class);
		am.load("coloring/210.png", Texture.class);
		am.load("coloring/211.png", Texture.class);
        am.load("coloring/butterfly.png", Texture.class);
        //todo am.load("coloring/butterfly_done.png", Texture.class);
        am.load("coloring/kitty.png", Texture.class);
        //todo am.load("coloring/kitty_done.png", Texture.class);
        am.load("coloring/rei.png", Texture.class);
        //todo am.load("coloring/rei_done.png", Texture.class);
        am.load("coloring/spruce.png", Texture.class);
        //todo am.load("coloring/spruce_done.png", Texture.class);

        am.load("coloring/thumbnails.txt", TextureAtlas.class);

        am.load("sound/click1.ogg", Sound.class);
        am.load("sound/rollover1.ogg", Sound.class);
        am.load("sound/switch1.ogg", Sound.class);
        am.load("sound/switch2.ogg", Sound.class);

        am.load("background/backgroundColorDesert.png", Texture.class);
        am.load("background/backgroundColorFall.png", Texture.class);
        am.load("background/backgroundColorForest.png", Texture.class);
        am.load("background/backgroundColorGrass.png", Texture.class);
        am.load("background/clouds.txt", TextureAtlas.class);

        am.load("brush.png", Texture.class);
        am.load("ui.json", Skin.class);
        am.load("textures.atlas", TextureAtlas.class);

        am.load("music/AcousticShuffle.mp3", Music.class);
        am.load("music/Folk Bed.mp3", Music.class);
        am.load("music/Hoedown.mp3", Music.class);
        am.load("music/NiceAndEasy.mp3", Music.class);
        am.load("music/OneFineDay.mp3", Music.class);
        am.load("music/menu/AcousticGuitar1.mp3", Music.class);
        am.load("music/menu/BridesBallad.mp3", Music.class);
        am.load("music/menu/CryinInMyBeer.mp3", Music.class);
        am.load("music/menu/GreenLeaves.mp3", Music.class);
        am.load("music/menu/HappyStrummin.mp3", Music.class);
        am.load("music/menu/MountainSun.mp3", Music.class);
    }

    private BitmapFont font;
    private SpriteBatch batch;
    private Label titleLabel;
    private Label progressLabel;

    @Override
    public void show() {
        FreeTypeFontGenerator fg = new FreeTypeFontGenerator(Gdx.files.internal("Comic_CAT.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "Загрузк0123456789%";
        parameter.size = 48;
        parameter.color = Color.WHITE;
        font = fg.generateFont(parameter);
        fg.dispose();
        batch = new SpriteBatch();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
        titleLabel = new Label("Загрузка", style);
        progressLabel = new Label("0%", style);
        getStage().addActor(titleLabel);
        getStage().addActor(progressLabel);
        progressLabel.setWidth(titleLabel.getWidth());
        progressLabel.setAlignment(Align.center);
        titleLabel.setPosition((getStage().getWidth() - titleLabel.getWidth())*.5f, getStage().getHeight()*.5f);
        progressLabel.setPosition(titleLabel.getX(), titleLabel.getY() - progressLabel.getHeight() - 4);
    }

    @Override
    public void hide() {
        font.dispose();
        batch.dispose();
        getStage().getActors().removeValue(titleLabel, true);
        getStage().getActors().removeValue(progressLabel, true);
    }

    /**
     * Флаг предотвращающий зацикливание при анимированой смене экрана
     */
    private boolean beginReplace = true;

    @Override
    public void render(float delta) {
        super.render(delta);
        if (beginReplace && Core.core().getManager().update()) {
            Core.core().initCore();
            Core.core().setStateToMenuScreen();
            beginReplace = false;
        }
        progressLabel.setText(String.format("%d%%",(int)(100*Core.core().getManager().getProgress())));
        getStage().draw();
        getStage().act();
    }
}
