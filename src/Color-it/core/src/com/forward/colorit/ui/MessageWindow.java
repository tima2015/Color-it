package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.forward.colorit.Core;

/**
 * Простое окно сообщений. Содержит в себе кнопку для закрытия окна
 */
public class MessageWindow extends Window {

    private static final String TAG = "MessageWindow";

    private TextButton ok;
    private Label message;

    /**
     * @param message Текст сообщения
     */
    public MessageWindow(String title, Skin skin, String message) {
        super(title, skin);
        this.message = new Label(message, Core.core().getUi(), Core.LABEL_STYLE_LARGE);

        initMessageWindow();
    }

    public MessageWindow(String title, Skin skin, String styleName, String message) {
        super(title, skin, styleName);
        this.message = new Label(message, Core.core().getUi());
        initMessageWindow();
    }

    public MessageWindow(String title, WindowStyle style, String message) {
        super(title, style);
        this.message = new Label(message, Core.core().getUi());
        initMessageWindow();
    }

    private void initMessageWindow(){
        setMovable(false);
        setModal(true);
        getTitleLabel().setAlignment(Align.center);

        add(message).pad(Core.UI_PADDING);
        row();
        ok = new TextButton("Да", Core.core().getUi());
        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(TAG, "clicked() called with on ok button");
                setVisible(false);
                getStage().getActors().removeValue(MessageWindow.this, true);
            }
        });
        ok.addListener(SoundClickListener.getInstance());
        add(ok).pad(Core.UI_PADDING);
        pad(Core.UI_PADDING);
        pack();
    }

    /**
     * @param message Текст сообщения.
     */
    public void setMessage(String message) {
        this.message.setText(message);
    }

    /**
     * @return Кнопка закрытия окна.
     */
    public TextButton getOk() {
        return ok;
    }
}
