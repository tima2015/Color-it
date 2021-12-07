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
    public static final float WORLD_WIDTH = 2000;
    public static final float WORLD_HEIGHT = 2000;
    private static final int upKey = Input.Keys.UP;
    private static final int downKey = Input.Keys.DOWN;
    private static final int rightKey = Input.Keys.RIGHT;
    private static final int leftKey = Input.Keys.LEFT;
    private static final float speed = 10;
    private static final float sectorSize = 50;


    private final Point head = new Point(Direction.UP);
    private final Point tail = new Point(Direction.UP);
    private final Queue<TurnPoint> turnPoints = new Queue<>();
    private final Rectangle border = new Rectangle(0,0, WORLD_WIDTH, WORLD_HEIGHT);
    private boolean die = false;

    private final Image headImg = new Image(Core.core().getTextures().findRegion("snake_head"));
    private final Image tailImg = new Image(Core.core().getTextures().findRegion("snake_tail"));
    private final Queue<SnakeTurnActor> snakeTurnActors = new Queue<>();
    private final Queue<SnakeBodyActor> snakeBodyActors = new Queue<>();

    public Snake() {
        headImg.setOrigin(Align.right);
        tailImg.setOrigin(Align.left);
        tail.getPos().x = WORLD_WIDTH*.5f;
        head.getPos().x = tail.getPos().x;
        head.getPos().y = sectorSize;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (die) return;
        updatePoint(head, delta);
        updatePoint(tail, delta);
        updateTailHead();
        checkBorder();
        checkTailReachFirstTurnPoint();
        handleKeys();
        die = checkCollision();
        if (die)
            fire(new GameEndEvent(false));
    }

    private void updateTailHead(){
        headImg.setRotation(head.getDirection().degree);
        headImg.setPosition(head.getPos().x, head.getPos().y);
        tailImg.setRotation(tail.getDirection().degree);
        tailImg.setPosition(tail.getPos().x, tail.getPos().y);
    }

    private void updatePoint(Point p, float delta){
        p.getPos().add(p.getDirection().direction_x * speed * delta, p.getDirection().direction_y * speed * delta);
    }

    public void turn(Direction to) {
        if (head.getDirection().equals(to)) return;
        snakeBodyActors.last().setB(turnPoints.first());
        TurnPoint turnPoint = new TurnPoint(to, head.getDirection());
        turnPoints.addLast(turnPoint);
        snakeTurnActors.addLast(new SnakeTurnActor(turnPoint));
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
        if ((turnPoints.first().getPos().x - tail.getPos().x) * tail.getDirection().direction_x < 0 ||
                (turnPoints.first().getPos().y - tail.getPos().y) * tail.getDirection().direction_y < 0) {
            turnPoints.removeFirst();
            snakeTurnActors.removeFirst();
            snakeBodyActors.removeFirst();
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
