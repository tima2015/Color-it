package com.forward.colorit.lines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.forward.colorit.Core;

/**
 * Ячейка в игре "Линии".
 */
class LinesCell extends Group {

    private static final String TAG = "LinesCell";

    private boolean selected = false;
    private boolean visited = false;
    private CellTextureState state = CellTextureState.EMPTY;

    private final Image orb = new Image();
    private final Image cellBackground = new Image(Core.core().getTextures().findRegion("lines_cell"));

    public LinesCell() {
        Gdx.app.debug(TAG, "LinesCell() called");
        TextureRegion tmpForSize = CellTextureState.BLUE.getRegion();
        orb.setSize(tmpForSize.getRegionWidth(), tmpForSize.getRegionHeight());
        addActor(cellBackground);
        setSize(cellBackground.getWidth(), cellBackground.getHeight());
        addActor(orb);
        resizeOrb();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        cellBackground.setSize(width, height);
        resizeOrb();
    }

    /**
     * @param state - состояние ячейки.
     */
    public void setState(CellTextureState state) {
        Gdx.app.debug(TAG, "setState() called with: state = [" + state + "]");
        this.state = state;
        setSelected(selected);
    }

    /**
     * @return Состояние ячейки.
     */
    public CellTextureState getState() {
        return state;
    }

    /**
     * @return Является ли данная ячейка выделенной.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Устанавливает значение selected и меняет текстуру ячейки
     * @param selected - является ли данная ячейка выделенной
     */
    public void setSelected(boolean selected) {
        Gdx.app.debug(TAG, "setSelected() called with: selected = [" + selected + "]");
        this.selected = selected;
        orb.setDrawable(state == CellTextureState.EMPTY ? null : new TextureRegionDrawable(state.getRegion()));
        resizeOrb();
    }

    /**
     * Изменяет размер изображения сферы, в зависимости от текущего её выделения
     */
    private void resizeOrb(){
        //Gdx.app.debug(TAG, "resizeOrb() called");
        if (selected)
            orb.setSize(getWidth() * .9f, getHeight() * .9f);
        else
            orb.setSize(getWidth() * .75f, getHeight() * .75f);
        orb.setPosition((getWidth() - orb.getWidth()) * .5f, (getHeight() - orb.getHeight()) * .5f);
    }

    /**
     * @return посещена ли ячейка при обхде.
     */
    public boolean isNotVisited() {
        return !visited;
    }

    /**
     * @param visited - значение о посещении ячейки.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
