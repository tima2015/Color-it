package com.forward.colorit.coloring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.forward.colorit.Core;
import com.forward.colorit.SubGameGroup;
import com.forward.colorit.lines.LinesSubGame;
import com.forward.colorit.ui.SoundClickListener;
import com.forward.colorit.ui.StageScreenAdapter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ColoringGameScreen extends StageScreenAdapter {

    private static final String TAG = "ColoringGameScreen";

    private static final int VIEWPORT_WIDTH = 1000;
    private static final int VIEWPORT_HEIGHT = 750;
    private static final int MARGINS = 24;
    private static final int PADDING = 12;

    private final SubGameGroup subGameGroup;
    private final Actor subGame;
    private final ColoringLevelData data;
    private final ColoringImage image;
    private final String levelName;
    private final Window gameInfo = new Window("", Core.core().getUi());
    private final Hashtable<Color, Integer> uncoloredFragmentsCounts = new Hashtable<>();
    private final ArrayList<Label> uncoloredFragmentsCountLabels = new ArrayList<>();

    public ColoringGameScreen(Actor subGame, ColoringLevelData data) {
        super(new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)), true);
        this.subGame = subGame;
        subGameGroup = subGame instanceof SubGameGroup ? (SubGameGroup) subGame : null;
        this.data = data;
        this.image = new ColoringImage(data.getImg());
        this.levelName = data.getId();
        initLevelData();
        addMusicsToPlayer();
        initSubGame();
        initImage();
        initSubGameInfoActor();
        initGameInfo();
    }

    private void initLevelData() {
        for (ColoringMap map : data.getMap()) {
            Color key = Color.valueOf(map.getColor());
            uncoloredFragmentsCounts.put(key, uncoloredFragmentsCounts.getOrDefault(key, 0) + 1);
        }
        Enumeration<Color> keys = uncoloredFragmentsCounts.keys();
        while (keys.hasMoreElements()) {
            Color key = keys.nextElement();
            Label label = new Label(getLocalizedColorName(key) + ": " + uncoloredFragmentsCounts.get(key), Core.core().getUi());
            uncoloredFragmentsCountLabels.add(label);
            label.setUserObject(key);
        }
    }

    private void addMusicsToPlayer() {
        if (subGame instanceof LinesSubGame) {
            getMusicPlayer().addMusic(Core.core().getManager().get("music/Hoedown.mp3", Music.class));
        }
    }

    @Override
    public void show() {
        Core.core().setBackground(Core.core().getManager().get("background/backgroundColorGrass.png", Texture.class));
        getMusicPlayer().setVolume(Core.getSettings().getMusicVolume());
        getMusicPlayer().start();
        Gdx.input.setInputProcessor(getStage());
    }

    private void initSubGame() {
        getStage().addActor(subGame);
        float subGameSize = VIEWPORT_HEIGHT - 2 * MARGINS;
        subGame.setSize(subGameSize, subGameSize);
        subGame.setPosition(MARGINS, MARGINS);
        subGame.addListener(new SubGameEventListener());
    }

    private void initGameEndWindow(String title) {
        Window window = new Window(title, Core.core().getUi(), Core.WINDOW_STYLE_PAUSE);
        window.setModal(true);
        window.getTitleLabel().setAlignment(Align.center);
        window.setMovable(false);
        getStage().addActor(window);

        TextButton mainMenuButton = new TextButton("В главное меню", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.core().setStateToMenuScreen();
            }
        });
        mainMenuButton.addListener(SoundClickListener.getInstance());
        window.add(mainMenuButton);

        window.pad(PADDING);
        window.pack();
        window.setPosition((getStage().getWidth() - window.getWidth()) * .5f, (getStage().getHeight() - window.getHeight()) * .5f);
    }

    private void initImage() {
        getStage().addActor(image);
        float imageSize = VIEWPORT_WIDTH - (subGame.getX() + subGame.getWidth() + PADDING + MARGINS);
        image.setSize(imageSize, imageSize);
        image.setPosition(subGame.getX() + subGame.getWidth() + PADDING, VIEWPORT_HEIGHT - imageSize - MARGINS);
    }

    private void initSubGameInfoActor() {
        if (subGameGroup == null) return;
        Actor infoActor = subGameGroup.getSubGameInfoActor();
        getStage().addActor(infoActor);
        infoActor.setSize(image.getWidth(), image.getHeight() * .5f);
        infoActor.setPosition(image.getX(), image.getY() - infoActor.getHeight() - PADDING);
    }

    private void initGameInfo() {
        getStage().addActor(gameInfo);

        gameInfo.setPosition(image.getX(), subGame.getY());
        gameInfo.pad(PADDING);
        gameInfo.add(new Label("Осталось закрасить:", Core.core().getUi())).padBottom(PADDING);
        for (Label label : uncoloredFragmentsCountLabels) {
            gameInfo.row();
            gameInfo.add(label).padBottom(PADDING);
        }
        gameInfo.row();
        TextButton pauseButton = new TextButton("Пауза", Core.core().getUi(), Core.TEXTBUTTON_STYLE_YELLOW);
        pauseButton.addListener(new PauseButtonClickListener());
        pauseButton.addListener(SoundClickListener.getInstance());
        gameInfo.add(pauseButton).expand().bottom();
        gameInfo.pack();
        gameInfo.setSize(image.getWidth(), (subGameGroup == null ? image.getY() : subGameGroup.getSubGameInfoActor().getY()) - MARGINS - PADDING);

    }

    static String getLocalizedColorName(Color color) {
        if (color.equals(Color.BLUE))
            return "Голубой";
        else if (color.equals(Color.RED))
            return "Красный";
        else if (color.equals(Color.YELLOW))
            return "Желтый";
        else if (color.equals(Color.GREEN))
            return "Зелёный";
        // TODO: 26.11.2021
        return "null";
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        getStage().getViewport().apply();
        getStage().draw();
        getStage().act(delta);
    }

    @Override
    public void hide() {
        super.hide();
        getMusicPlayer().stop();
    }

    @Override
    public void dispose() {
        super.dispose();
        image.dispose();
    }

    private class SubGameEventListener implements EventListener {

        @Override
        public boolean handle(Event event) {
            if (event instanceof ColoringEvent) return handleColoringEvent((ColoringEvent) event);
            if (event instanceof GameEndEvent) return handleGameEndEvent((GameEndEvent) event);
            return false;
        }

        private boolean handleColoringEvent(ColoringEvent event) {
            Gdx.app.debug(TAG, "handleColoringEvent() called with: event = [" + event + "]");
            boolean result = false;
            for (int j = 0; j < event.coloringCount; j++)
                for (int i = 0; i < data.getMap().length; i++) {
                    if (data.getMap()[i] != null && Color.valueOf(data.getMap()[i].getColor()).equals(event.color)) {
                        image.color(data.getMap()[i]);
                        data.getMap()[i] = null;
                        uncoloredFragmentsCounts.put(event.color, uncoloredFragmentsCounts.getOrDefault(event.color, 0) - 1);
                        updateUncoloredFragmentsCountLabels();
                        checkWin();
                        result = true;
                        break;
                    }
                }
            return result;
        }

        private boolean handleGameEndEvent(GameEndEvent event) {
            initGameEndWindow("Вы проиграли.");
            return true;
        }

        private void updateUncoloredFragmentsCountLabels() {
            for (Label label : uncoloredFragmentsCountLabels) {
                label.setText(getLocalizedColorName((Color) label.getUserObject()) + ": "
                        + uncoloredFragmentsCounts.getOrDefault((Color) label.getUserObject(), 0));
            }
        }

        private void checkWin() {
            Enumeration<Color> keys = uncoloredFragmentsCounts.keys();
            boolean win = true;

            while (keys.hasMoreElements()) {
                Color key = keys.nextElement();
                if (uncoloredFragmentsCounts.get(key) != 0)
                    win = false;
            }

            if (win) {
                Core.getProgressData().setLevelComplete(levelName);
                initGameEndWindow("Вы выйграли!");
            }
        }
    }

    private static class PauseButtonClickListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Window window = new Window("Пауза", Core.core().getUi(), Core.WINDOW_STYLE_PAUSE);
            window.setModal(true);
            window.getTitleLabel().setAlignment(Align.center);
            window.setMovable(false);
            event.getListenerActor().getStage().addActor(window);
            Stage stage = event.getListenerActor().getStage();
            TextButton resumeButton = new TextButton("Продолжить", Core.core().getUi(), Core.TEXTBUTTON_STYLE_GREEN);
            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    window.setVisible(false);
                    window.getStage().getActors().removeValue(window, true);
                }
            });
            resumeButton.addListener(SoundClickListener.getInstance());
            window.row();
            window.add(resumeButton).padBottom(PADDING);
            TextButton mainMenuButton = new TextButton("В главное меню", Core.core().getUi(), Core.TEXTBUTTON_STYLE_RED);
            mainMenuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Core.core().setStateToMenuScreen();
                }
            });
            mainMenuButton.addListener(SoundClickListener.getInstance());
            window.row();
            window.add(mainMenuButton);
            window.pad(PADDING);
            window.pack();
            window.setPosition((stage.getWidth() - window.getWidth()) * .5f, (stage.getHeight() - window.getHeight()) * .5f);
        }
    }
}
