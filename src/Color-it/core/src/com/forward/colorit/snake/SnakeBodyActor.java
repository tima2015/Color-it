package com.forward.colorit.snake;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.forward.colorit.Core;
import com.forward.colorit.tool.Direction;

public class SnakeBodyActor extends Image {
    private final Animation<TextureRegion> animation;
    private Point a;
    private Point b;

    private float dt;

    public SnakeBodyActor(Point a, Point b) {
        this.a = a;
        this.b = b;
        animation = new Animation<TextureRegion>(1 / 60f, Core.core().getTextures().findRegions("snakeBody"), Animation.PlayMode.LOOP);
        setDt(0);
        setOrigin(a.getDirection().equals(Direction.UP) ? Align.bottom :
                a.getDirection().equals(Direction.DOWN) ? Align.top : a.getDirection().equals(Direction.LEFT) ? Align.right : Align.left);
        setRotation(new Vector2(a.getDirection().direction_x, a.getDirection().direction_y).angleDeg());
        setPosition(a.getPos().x, a.getPos().y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public float getDt() {
        return dt;
    }

    public void setDt(float dt) {
        this.dt = dt;
        setDrawable(new TiledDrawable(animation.getKeyFrame(dt)));
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }
}
