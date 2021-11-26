package com.forward.colorit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

class LoadingScreen extends ScreenAdapter {

    private static final LoadingScreen loadingScreen = new LoadingScreen();

    static LoadingScreen getInstance() {
        return loadingScreen;
    }

    private LoadingScreen(){
        load();
    }

    private void load(){
        AssetManager am = Core.core().getManager();
        am.load("coloring/ankha.png", Texture.class);
        am.load("coloring/ankha_done.png", Texture.class);
        am.load("coloring/butterfly.png", Texture.class);
        //todo am.load("coloring/butterfly_done.png", Texture.class);
        am.load("coloring/cake.png", Texture.class);
        //todo am.load("coloring/cake_done.png", Texture.class);
        am.load("coloring/fish.png", Texture.class);
        //todo am.load("coloring/fish_done.png", Texture.class);
        am.load("coloring/flower.png", Texture.class);
        //todo am.load("coloring/flower_done.png", Texture.class);
        am.load("coloring/kitty.png", Texture.class);
        //todo am.load("coloring/kitty_done.png", Texture.class);
        am.load("coloring/rat.png", Texture.class);
        //todo am.load("coloring/rat_done.png", Texture.class);
        am.load("coloring/rei.png", Texture.class);
        //todo am.load("coloring/rei_done.png", Texture.class);
        am.load("coloring/spruce.png", Texture.class);
        //todo am.load("coloring/spruce_done.png", Texture.class);

        am.load("coloring/thumbnails.txt", TextureAtlas.class);

        am.load("sound/click1.ogg", Sound.class);
        am.load("sound/click2.ogg", Sound.class);
        am.load("sound/rollover1.ogg", Sound.class);
        am.load("sound/rollover2.ogg", Sound.class);
        am.load("sound/switch2.ogg", Sound.class);
        am.load("sound/switch3.ogg", Sound.class);

        am.load("background/backgroundColorDesert.png", Texture.class);
        am.load("background/backgroundColorFall.png", Texture.class);
        am.load("background/backgroundColorForest.png", Texture.class);
        am.load("background/backgroundColorGrass.png", Texture.class);
        am.load("background/clouds.txt", TextureAtlas.class);

        am.load("brush.png", Texture.class);
        am.load("ui.json", Skin.class);
        am.load("textures.atlas", TextureAtlas.class);
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
        progressLabel.setWidth(titleLabel.getWidth());
        progressLabel.setAlignment(Align.center);
    }

    @Override
    public void hide() {
        font.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        if (Core.core().getManager().update()) {
            Core.core().initCore();
            Core.core().setStateToMenuScreen();
            return;
        }
        titleLabel.setPosition((Gdx.graphics.getWidth() - titleLabel.getWidth())*.5f, Gdx.graphics.getHeight()*.5f);
        progressLabel.setPosition(titleLabel.getX(), titleLabel.getY() - progressLabel.getHeight() - 4);
        progressLabel.setText(String.format("%d%%",(int)(100*Core.core().getManager().getProgress())));
        batch.begin();
        titleLabel.draw(batch, 1);
        progressLabel.draw(batch, 1);
        batch.end();
    }
}
