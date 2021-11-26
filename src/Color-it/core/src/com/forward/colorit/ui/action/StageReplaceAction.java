package com.forward.colorit.ui.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.forward.colorit.Core;
import com.forward.colorit.ui.StageScreenAdapter;

/**
 * Эффект для перехода между экранами. Затемнение
 */
public class StageReplaceAction extends Action {

    private static final String TAG = "StageReplaceAction";

    private final StageScreenAdapter old;
    private final StageScreenAdapter current;
    private final float half_duration;
    private boolean screenReplaced = false;
    private float timer;
    private ShaderProgram shader;

    public StageReplaceAction(StageScreenAdapter old, StageScreenAdapter current, float duration){
        Gdx.app.debug(TAG, "StageReplaceAction() called with: old = [" + old + "], current = [" + current + "], duration = [" + duration + "]");
        this.old = old;
        this.current = current;
        this.half_duration = duration*.5f;
        timer = duration*.5f;
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shader/alpha.vert"), Gdx.files.internal("shader/alpha.frag"));
        if (!shader.isCompiled())
            Gdx.app.error(TAG, "StageReplaceAction: " + shader.getLog());
        old.getStage().getBatch().setShader(shader);
        Core.core().getBackgroundSpriteBatch().setShader(shader);
    }

    @Override
    public boolean act(float delta) {
        float da = timer/half_duration;
        shader.setAttributef("alpha_color", 1,1,1,da);
        if (screenReplaced){
            timer += delta;
            if (timer >= half_duration){
                Gdx.app.debug(TAG, "act: StageReplaceAction finished!");
                current.getStage().getBatch().setShader(null);
                Core.core().getBackgroundSpriteBatch().setShader(null);

                shader.dispose();
                return true;
            }
        } else {
            timer -= delta;
            if (timer <= 0){
                screenReplaced = true;
                timer = 0;
                Core.core().setScreen(current);
                old.getStage().getBatch().setShader(null);
                current.getStage().getBatch().setShader(shader);
                current.getStage().addAction(this);
                if (old.isAutoDispose()) old.dispose();
            }
        }
        return false;
    }
}
