package com.forward.colorit.ui.action;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.forward.colorit.Core;

/**
 * Действие полёта облаков.
 * Создаёт случайное облако, на случайной высоте которое движется справа на лево.
 */
public class CloudFlyAction extends Action {

    private final Stage stage;
    private final Image cloud;
    private final float cloudSpeed;
    private boolean finished = false;

    public CloudFlyAction(Stage stage){
        this.stage = stage;
        Array<TextureRegion> regions = new Array<>(Core.core().getManager().get("background/clouds.txt", TextureAtlas.class).getRegions());
        regions.shuffle();
        cloud = new Image(regions.get(0));
        stage.addActor(cloud);
        cloud.setPosition(-cloud.getWidth(), MathUtils.random(stage.getHeight()*.2f, stage.getHeight()*.9f));
        cloudSpeed = MathUtils.random(10f, 80f);
    }

    @Override
    public boolean act(float delta) {
        if (cloud.getX() > stage.getWidth()) {
            stage.getActors().removeValue(cloud, true);
            finished = true;
            return true;
        }
        cloud.setX(cloud.getX() + delta*cloudSpeed);
        return false;
    }

    public boolean isFinished() {
        return finished;
    }
}
