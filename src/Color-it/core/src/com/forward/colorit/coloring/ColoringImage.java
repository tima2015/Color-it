package com.forward.colorit.coloring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.forward.colorit.Core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

class ColoringImage extends Actor implements Disposable {
    private Pixmap pixmap;
    private Sprite sprite;
    private Texture current = null;

    ColoringImage(String img) {
        Texture texture = Core.core().getManager().get(img);
        sprite = new Sprite(texture);

        pixmap = new Pixmap(Gdx.files.internal(img));
        pixmap.setBlending(Pixmap.Blending.None);
        wbPixmap();
    }

    void color(ColoringMap map) {
        addAction(new FillingActon(map.getX(), map.getY(), Color.valueOf(map.getTargetColor())));
    }

    private void wbPixmap(){
        pixmap.setColor(Color.BLACK);
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                if (pixmap.getPixel(x,y) != WHITE_INTS) pixmap.fillCircle(x,y, 0);
            }
        }
    }

    private void updateTexture() {
        if (current != null)
            current.dispose();
        current = new Texture(pixmap);
        sprite.setTexture(current);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sprite.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public void dispose() {

        Array<Action> actions = getActions();
        for (Action action : actions) {
            if (action instanceof Disposable){
                Disposable disposable = (Disposable) action;
                disposable.dispose();
            }
        }
        pixmap.dispose();
    }

    private static final int BLACK_INTS = Color.BLACK.toIntBits();
    private static final int WHITE_INTS = Color.WHITE.toIntBits();
    /**
     * ???????????????????? ???????????????? ?????????????? ???? ??????
     */
    private static final int FILLING_ITERATION = 500;

    /**
     * ?????????? ????????????????, ?????????????????????? ???????????????????????? ?????????????? ?????????????? ??????????
     */
    private class FillingActon extends Action implements Disposable{
        private static final String TAG = "FillingActon";

        private boolean disposed = false;

        private final Color color;
        private final Stack<GridPoint2> fillStack = new Stack<>();

        private final Thread thread = new Thread(this::run);

        FillingActon(int x, int y, Color color) {
            this.color = color;
            fillStack.push(new GridPoint2(x, y));
            thread.start();
            Gdx.app.debug(TAG, "Filling action start with: x = [" + x + "], y = [" + y + "], color = [" + color + "]");
        }

        @Override
        public boolean act(float delta) {
            updateTexture();
            if (fillStack.empty()) {
                    Gdx.app.debug(TAG, "Filling action finished!");
                    return true;
            }
            return false;
        }

        @Override
        public synchronized void dispose() {
            disposed = true;
        }

        private synchronized void run() {
            while (!fillStack.empty()) {
                for (int i = 0; i < FILLING_ITERATION; i++) {
                    if (disposed) {
                        fillStack.clear();
                        return;
                    }
                    if (fillStack.empty())
                        return;
                    GridPoint2 p = fillStack.pop();
                    if (p.x <= 0 || p.y <= 0 || p.x >= pixmap.getWidth() || p.y >= pixmap.getHeight()) break;
                    int pixel = pixmap.getPixel(p.x, p.y);
                    if (pixel != color.toIntBits() && pixel == WHITE_INTS) {
                        pixmap.setColor(color);
                        pixmap.fillCircle(p.x, p.y, 0);
                        List<GridPoint2> nextPoints = Arrays.asList(
                                new GridPoint2(p.x - 1, p.y),
                                new GridPoint2(p.x, p.y - 1),
                                new GridPoint2(p.x, p.y + 1),
                                new GridPoint2(p.x + 1, p.y));
                        Collections.shuffle(nextPoints, MathUtils.random);
                        for (GridPoint2 nextPoint : nextPoints) {
                            fillStack.push(nextPoint);
                        }
                    }
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Gdx.app.error(TAG, "run: ", e);
                    return;
                }
            }
        }
    }
}
