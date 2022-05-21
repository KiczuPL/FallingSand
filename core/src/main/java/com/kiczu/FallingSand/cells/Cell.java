package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public abstract class Cell {


    protected Point position;
    protected Color color;
    protected float mass;

    protected Vector2 speed;

    public Cell(Point position) {
        this.position = position;
    }


    public Color getColor() {
        return color;
    }

    public float getMass() {
        return mass;
    }

    public Vector2 getSpeed() {
        return speed;
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
