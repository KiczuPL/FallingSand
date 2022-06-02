package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

public abstract class Cell {


    protected Vector2 position;
    protected Vector2 velocity;
    protected Color color;
    protected float mass;

    protected boolean isUpdated;


    public Cell(Vector2 position) {
        this.position = position;
        isUpdated = false;
        velocity = new Vector2(0, 0);
    }

    public Color getColor() {
        return color;
    }

    public float getMass() {
        return mass;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void isUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public abstract void update(GameMap matrix, CellContainer parentContainer);

    protected boolean isPositionEmpty(GameMap matrix, Vector2 destination){
        return matrix.getCellAtPosition(destination) instanceof EmptyCell;
    }

    protected boolean canMoveToPoint(GameMap matrix, CellContainer parentContainer, Vector2 destination) {
        if (!matrix.isPointInBounds(destination))
            return false;
        CellContainer container = matrix.getCellContainer(destination);

        return container.canCellMoveHere(this);
    }

    protected void moveToPoint(GameMap matrix, Vector2 destinationVector2) {
        matrix.swapCellsAtPosition(this.position, destinationVector2);
    }

}
