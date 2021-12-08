package com.forward.colorit.snake;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.forward.colorit.Core;

public class SnakeTurnActor extends Image {
    private final TurnPoint point;
    //private final Animation<TextureRegion> animation;

    private float dt;

    public SnakeTurnActor(TurnPoint point) {
        super(Core.core().getTextures().findRegion("snake_turn"));
        this.point = point;
        //animation = new Animation<>(1 / 60f, Core.core().getTextures().findRegions("snakeTurn"), Animation.PlayMode.LOOP);
        setDt(0);
        setOrigin(Align.center);
        setRotation(new Vector2(point.getDirection().direction_x, point.getDirection().direction_y).angleDeg());
        setPosition(point.getPos().x, point.getPos().y);
        toFront();
    }

    public float getDt() {
        return dt;
    }

    public void setDt(float dt) {
        this.dt = dt;
        //setDrawable(new TextureRegionDrawable(animation.getKeyFrame(dt)));
    }

    public TurnPoint getPoint() {
        return point;
    }
}
