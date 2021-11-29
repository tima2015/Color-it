package com.forward.colorit.ui.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.forward.colorit.Core;
import com.forward.colorit.ui.StageScreenAdapter;

/**
 * Эффект для перехода между экранами. Затемнение и заглушение музыки
 */
public class StageReplaceAction extends Action {

    private static final String TAG = "StageReplaceAction";

    private final StageScreenAdapter old;
    private final StageScreenAdapter current;
    private final ShaderProgram shader;
    private final float state_duration;
    private float timer = 0;
    private State state = State.BEGIN;

    /**
     * @param old      экран из которого происходит переход
     * @param current  экран назначения
     * @param duration длительность перехода
     */
    public StageReplaceAction(StageScreenAdapter old, StageScreenAdapter current, float duration) {
        Gdx.app.debug(TAG, "StageReplaceAction() called with: old = [" + old + "], current = [" + current + "], duration = [" + duration + "]");
        this.old = old;
        this.current = current;
        this.state_duration = duration / 3f;
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shader/alpha.vert"), Gdx.files.internal("shader/alpha.frag"));
        if (!shader.isCompiled())
            Gdx.app.error(TAG, "StageReplaceAction: " + shader.getLog());
    }

    @Override
    public boolean act(float delta) {
        timer += delta;
        State tmp = state;
        state.act(this);
        if (tmp != state) return false;
        return state == State.END;
    }

    private void setState(State state) {
        Gdx.app.debug(TAG, "setState() called with: state = [" + state + "]");
        timer = 0;
        this.state = state;
    }

    private enum State {
        BEGIN {
            @Override
            void act(StageReplaceAction action) {
                action.old.getStage().getBatch().setShader(action.shader);
                Core.core().getBackgroundStage().getBatch().setShader(action.shader);
                action.current.getStage().getBatch().setShader(action.shader);
                action.setState(OLD);
            }
        }, OLD {
            @Override
            void act(StageReplaceAction action) {
                float delta = 1f - action.timer / action.state_duration;
                action.shader.setAttributef("alpha_color", 1, 1, 1, delta);
                action.old.getMusicPlayer().setVolume(Math.min(delta, Core.getSettings().getMusicVolume()));
                if (action.timer >= action.state_duration) {
                    action.setState(SPACE);
                    Core.core().setScreen(null);
                }
            }
        }, SPACE {
            @Override
            void act(StageReplaceAction action) {
                action.shader.setAttributef("alpha_color", 1, 1, 1, 0);
                if (action.timer >= action.state_duration) {
                    action.setState(CURRENT);
                    Core.core().setScreen(action.current);
                }
            }
        }, CURRENT {
            @Override
            void act(StageReplaceAction action) {
                float delta = action.timer / action.state_duration;
                action.shader.setAttributef("alpha_color", 1, 1, 1, delta);
                action.current.getMusicPlayer().setVolume(Math.min(delta, Core.getSettings().getMusicVolume()));
                if (action.timer >= action.state_duration) action.setState(END);
            }
        }, END {
            @Override
            void act(StageReplaceAction action) {
                action.old.getStage().getBatch().setShader(null);
                Core.core().getBackgroundStage().getBatch().setShader(null);
                action.current.getStage().getBatch().setShader(null);
                action.old.getMusicPlayer().setVolume(Core.getSettings().getMusicVolume());
                action.current.getMusicPlayer().setVolume(Core.getSettings().getMusicVolume());
                action.shader.dispose();
                if (action.old.isAutoDispose()) action.old.dispose();
            }
        };

        abstract void act(StageReplaceAction action);
    }
}
