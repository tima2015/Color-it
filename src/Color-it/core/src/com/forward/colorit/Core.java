package com.forward.colorit;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forward.colorit.coloring.Settings;
import com.forward.colorit.ui.MenuScreen;

public class Core extends Game {

	public static final String TEXTBUTTON_STYLE_YELLOW = "yellow";
	public static final String TEXTBUTTON_STYLE_GREEN = "green";
	public static final String TEXTBUTTON_STYLE_RED = "red";
	public static final String LABEL_STYLE_LARGE = "large";
	public static final String LABEL_STYLE_SMALL = "small";

	public static Core core(){
		return (Core) Gdx.app.getApplicationListener();
	}

	private static Settings settings;

	public static Settings getSettings() {
		return settings;
	}

	public Core(){}

	///////////////////////////////////////////////////////////////////
	//////Вот это противное поле и конструктор, тут чисто для тестов///
	private Screen current;                                         ///
	public Core(Screen screen){                                     ///fixme
		current = screen;                                           ///
	}                                                               ///
	///////////////////////////////////////////////////////////////////


	private TextureAtlas textures;
	private Texture background;
	private SpriteBatch backgroundSpriteBatch;
	private Viewport viewport = new ScreenViewport();
	private Skin ui;
	private MenuScreen menuScreen;

	private Cursor cursor;
	private Pixmap pixmapCursor;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);// FIXME: 24.11.2021
		textures = new TextureAtlas("textures.atlas");
		backgroundSpriteBatch = new SpriteBatch();
		background = new Texture("background-1.jpg");
		ui = new Skin(Gdx.files.internal("ui.json"));
		menuScreen = new MenuScreen();
		initCursor();
		settings = new Settings();
		setStateToMenuScreen();
		if (current != null) setScreen(current);// FIXME: 21.11.2021
	}

	private void initCursor(){
		pixmapCursor = new Pixmap(Gdx.files.internal("brush.png"));
		cursor = Gdx.graphics.newCursor(pixmapCursor, 1, 1);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		viewport.apply();
		backgroundSpriteBatch.setProjectionMatrix(viewport.getCamera().projection);
		backgroundSpriteBatch.begin();
		int size = Math.max(viewport.getScreenWidth(), viewport.getScreenHeight());
		backgroundSpriteBatch.draw(background, -size*.5f,-size*.5f, size, size);
		backgroundSpriteBatch.end();
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		background.dispose();
		backgroundSpriteBatch.dispose();
		textures.dispose();
	}

	public TextureAtlas getTextures() {
		return textures;
	}

	public void setBackground(Texture background) {
		this.background.dispose();// TODO: 22.11.2021 replase witch assetManageer
		this.background = background;
	}

	public Skin getUi() {
		return ui;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setStateToMenuScreen(){
		Screen old = getScreen();
		setScreen(menuScreen);
		if (old == null || old.equals(menuScreen)) return;
		old.dispose();
	}
}
