package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public abstract class Cell {

    protected Color color;
    protected float density;


    public Color getColor() {
        return color;
    }

    public float getDensity() {
        return density;
    }

    public abstract void update(GameMap matrix, CellContainer parentContainer);

    protected boolean canMoveToPoint(GameMap matrix, CellContainer parentContainer, Point destinationPoint) {
        if (!matrix.isPointInBounds(destinationPoint))
            return false;
        CellContainer destination = matrix.getCellContainer(destinationPoint);

        return destination.canCellMoveHere(this);
    }

    protected void moveToPoint(GameMap matrix, CellContainer parentContainer, Point destinationPoint) {
        CellContainer destination = matrix.getCellContainer(destinationPoint);
        matrix.swapCellContainersContents(parentContainer, destination);
    }

}
