package com.forward.colorit.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.forward.colorit.Core;

/**
 * Реализация окна подтверждения "Да"-"Нет"
 */
public class DialogWindow extends Window {

    private static final String TAG = "DialogWindow";

    private TextButton ok;
    private TextButton cancel;

    /**
     * @param onOk Действие выполняемое при нажатии кнопки "Да"
     * @param onCancel Действие выполняемое при нажатии кнопки "Нет"
     */
    public DialogWindow(String title, Skin skin, Runnable onOk, Runnable onCancel) {
        super(title, skin);
        initDialogWindow(onOk, onCancel);
    }

    public DialogWindow(String title, Skin skin, String styleName, Runnable onOk, Runnable onCancel) {
        super(title, skin, styleName);
        initDialogWindow(onOk, onCancel);
    }

    public DialogWindow(String title, WindowStyle style, Runnable onOk, Runnable onCancel) {
        super(title, style);
        initDialogWindow(onOk, onCancel);
    }

    private void initDialogWindow(Runnable onOk, Runnable onCancel){
        ok = new SoundTextButton("Да", Core.core().getUi());
        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(TAG, "clicked() called with on ok button");
                onOk.run();
                setVisible(false);
                getStage().getActors().removeValue(DialogWindow.this, true);
            }
        });
        add(ok).pad(Core.UI_PADDING);

        cancel = new SoundTextButton("Нет", Core.core().getUi());
        cancel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(TAG, "clicked() called with on cancel button");
                onCancel.run();
                setVisible(false);
                getStage().getActors().removeValue(DialogWindow.this, true);
            }
        });
        add(cancel).pad(Core.UI_PADDING);
        pad(Core.UI_PADDING);
        pack();
        setPosition((getStage().getWidth() - getWidth())*.5f, (getStage().getHeight() - getHeight())*.5f);
    }

    public TextButton getOk() {
        return ok;
    }

    public TextButton getCancel() {
        return cancel;
    }
}
