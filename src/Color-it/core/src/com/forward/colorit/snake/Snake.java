package com.forward.colorit.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.forward.colorit.Core;
import com.forward.colorit.coloring.GameEndEvent;
import com.forward.colorit.tool.Direction;

public class Snake extends WidgetGroup{
    private static final String TAG = "Snake";

    public static final float WORLD_WIDTH = 2000;
    public static final float WORLD_HEIGHT = 2000;
    private static final int upKey = Input.Keys.UP;
    private static final int downKey = Input.Keys.DOWN;
    private static final int rightKey = Input.Keys.RIGHT;
    private static final int leftKey = Input.Keys.LEFT;
    private static final float speed = 50;
    private static final float sectorSize = 200;


    private final Point head = new Point(Direction.UP);
    private final Point tail = new Point(Direction.UP);
    private final Queue<TurnPoint> turnPoints = new Queue<>();
    private final Rectangle border = new Rectangle(0,0, WORLD_WIDTH, WORLD_HEIGHT);
    private boolean die = false;
    private boolean paused = false;

    private final Image headImg = new Image(Core.core().getTextures().findRegion("snake_head"));
    private final Image tailImg = new Image(Core.core().getTextures().findRegion("snake_tail"));
    private final Queue<SnakeTurnActor> snakeTurnActors = new Queue<>();
    private final Queue<SnakeBodyActor> snakeBodyActors = new Queue<>();

    public Snake() {
        addActor(headImg);
        addActor(tailImg);
        headImg.toFront();
        tailImg.toFront();
        tail.getPos().x = WORLD_WIDTH*.5f;
        head.getPos().x = tail.getPos().x;
        head.getPos().y = sectorSize*2;

        SnakeBodyActor firstBody = new SnakeBodyActor(tail, head);
        addActor(firstBody);
        firstBody.toBack();
        snakeBodyActors.addLast(firstBody);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (die || paused) return;
        updatePoint(head, delta);
        updatePoint(tail, delta);
        updateBody();
        checkTailReachFirstTurnPoint();
        handleKeys();
        die = checkCollision();
        checkBorder();
        if (die)
            fire(new GameEndEvent(false));
    }

    private void updateBody(){
        float ratioH = getHeight() / WORLD_HEIGHT;
        float ratioW = getWidth() / WORLD_WIDTH;
        headImg.setSize(sectorSize * ratioW, sectorSize * ratioH);
        tailImg.setSize(sectorSize * ratioW, sectorSize * ratioH);
        headImg.setOrigin(Align.center);
        tailImg.setOrigin(Align.center);
        headImg.setRotation(head.getDirection().degree);
        headImg.setPosition(head.getPos().x * ratioW, head.getPos().y * ratioH);
        tailImg.setRotation(tail.getDirection().degree);
        tailImg.setPosition(tail.getPos().x * ratioW, tail.getPos().y * ratioH);
        for (SnakeTurnActor turnActor : snakeTurnActors) {
            turnActor.setSize(sectorSize * ratioW, sectorSize * ratioH);
            turnActor.setOrigin(Align.center);
            turnActor.setPosition(turnActor.getPoint().getPos().x * ratioW, turnActor.getPoint().getPos().y * ratioH);
        }
        for (SnakeBodyActor bodyActor : snakeBodyActors) {
            switch (bodyActor.getA().getDirection()) {
                case UP:
                case DOWN:
                    bodyActor.setSize(sectorSize * ratioW, Math.abs(bodyActor.getB().getPos().y - bodyActor.getA().getPos().y) * ratioH);
                    break;
                default:
                    bodyActor.setSize(Math.abs(bodyActor.getB().getPos().x - bodyActor.getA().getPos().x) * ratioW, sectorSize * ratioH);
                    break;
            }
            bodyActor.setOrigin(Align.center);
            bodyActor.setPosition((bodyActor.getA().getPos().x + (bodyActor.getB().getPos().x - bodyActor.getA().getPos().x)*.5f) * ratioW,
                    (bodyActor.getA().getPos().y + (bodyActor.getB().getPos().y - bodyActor.getA().getPos().y)*.5f) * ratioH);
        }
    }

    private void updatePoint(Point p, float delta){
        p.getPos().add(p.getDirection().direction_x * speed * delta, p.getDirection().direction_y * speed * delta);
    }

    public void turn(Direction to) {
        Gdx.app.debug(TAG, "turn() called with: to = [" + to + "]");
        if (head.getDirection().equals(to)) return;
        TurnPoint turnPoint = new TurnPoint(to, head.getDirection());
        turnPoint.getPos().set(head.getPos());
        head.setDirection(to);
        if (snakeBodyActors.notEmpty()) snakeBodyActors.last().setB(turnPoint);
        turnPoints.addLast(turnPoint);
        snakeTurnActors.addLast(new SnakeTurnActor(turnPoint));
        addActor(snakeTurnActors.last());
        snakeBodyActors.addLast(new SnakeBodyActor(turnPoint, head));
    }

    //Не самый правильный метод обработки клавиш, но в нашем случае, наиболее удобный
    public void handleKeys(){
        if (Gdx.input.isKeyJustPressed(upKey)) turn(Direction.UP);
        if (Gdx.input.isKeyJustPressed(downKey)) turn(Direction.DOWN);
        if (Gdx.input.isKeyJustPressed(rightKey)) turn(Direction.RIGHT);
        if (Gdx.input.isKeyJustPressed(leftKey)) turn(Direction.LEFT);
    }

    private void checkBorder(){
        if (!border.contains(head.getPos())) die = true;
    }

    private void checkTailReachFirstTurnPoint(){
        if (turnPoints.isEmpty()) return;
        if ((turnPoints.first().getPos().x - tail.getPos().x) * tail.getDirection().direction_x < 0 ||
                (turnPoints.first().getPos().y - tail.getPos().y) * tail.getDirection().direction_y < 0) {
            Gdx.app.debug(TAG, "checkTailReachFirstTurnPoint: tail reach point!");
            turnPoints.removeFirst();
            getChildren().removeValue(snakeTurnActors.removeFirst(), true);
            getChildren().removeValue(snakeBodyActors.removeFirst(), true);
            if (turnPoints.notEmpty()) {
                tail.setDirection(turnPoints.last().getDirection());
            } else {
                tail.setDirection(head.getDirection());
            }
            snakeBodyActors.last().setA(tail);
        }
    }

    private boolean checkCollision(){
        // Если количество точек поворота меньше 3, то столкновение невозможно
        if (turnPoints.size < 3)
            return false;
        if (intersectLines(tail.getPos(), turnPoints.first().getPos()))
            return true;
        for (int i = 1; i < turnPoints.size - 1; i++)
            if (intersectLines(turnPoints.get(i - 1).getPos(), turnPoints.get(i).getPos()))
                return true;

        return false;
    }

    private boolean intersectLines(Vector2 p1, Vector2 p2) {
        return Intersector.intersectLines(turnPoints.last().getPos(), head.getPos(), p1, p2, null);
    }

    public Point getHead() {
        return head;
    }

    public Point getTail() {
        return tail;
    }

    public Queue<TurnPoint> getTurnPoints() {
        return turnPoints;
    }

    public boolean isDie() {
        return die;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public final class TurnClickListener extends ClickListener {
        private final Direction direction;

        private TurnClickListener(Direction direction){
            switch (direction) {
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                    break;
                default:
                    throw new IllegalArgumentException("Direction must be UP, DOWN, RIGHT or LEFT!");
            }
            this.direction = direction;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            turn(direction);
        }
    }
}
