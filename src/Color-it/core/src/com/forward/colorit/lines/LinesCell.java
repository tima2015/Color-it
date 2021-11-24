package com.forward.colorit.lines;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

class LinesCell extends Actor {

    private boolean selected = false;
    private boolean visited = false;
    private CellTextureState state = CellTextureState.EMPTY;

    private final Sprite sprite = new Sprite(state.getRegion());

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

    public void setState(CellTextureState state) {
        this.state = state;
        setSelected(selected);
    }

    public CellTextureState getState() {
        return state;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        if (selected) switch (state) {
            case RED:
                state = CellTextureState.RED_SELECTED;
                break;
            case BLUE:
                state = CellTextureState.BLUE_SELECTED;
                break;
            case GREEN:
                state = CellTextureState.GREEN_SELECTED;
                break;
            case YELLOW:
                state = CellTextureState.YELLOW_SELECTED;
                break;
        } else switch (state){
            case RED_SELECTED:
                state = CellTextureState.RED;
                break;
            case BLUE_SELECTED:
                state = CellTextureState.BLUE;
                break;
            case GREEN_SELECTED:
                state = CellTextureState.GREEN;
                break;
            case YELLOW_SELECTED:
                state = CellTextureState.YELLOW;
                break;
        }
        sprite.setRegion(state.getRegion());
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
