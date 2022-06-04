package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.interfaces.Aging;
import com.kiczu.FallingSand.cells.interfaces.Flammable;
import com.kiczu.FallingSand.cells.solid.immovable.ImmovableSolid;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

public abstract class Cell {


    protected Vector2 position;
    protected Vector2 velocity;
    protected Color color;
    protected float mass;

    protected int heatCapacity;
    protected int heatConductivity;
    protected int temperature;

    protected boolean isBurning;
    protected boolean isRemoved;


    protected int lifeSpan;
    protected int hitPoints;
    protected int maxHitPoints;

    protected float flammabilityResistance;


    protected boolean isUpdated;
    protected boolean collidedLastFrame;
    protected boolean movedInLastFrame;


    public Cell(Vector2 position) {
        this.position = position;
        isUpdated = false;
        isBurning = false;
        isRemoved = false;
        maxHitPoints = 100;
        hitPoints = maxHitPoints;
        flammabilityResistance = 1f;

        velocity = new Vector2(0, 0);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

    protected boolean canMoveToPosition(GameMap matrix, Vector2 destination) {
        if (!matrix.isPointInBounds(destination))
            return false;
        if (matrix.getCellAtPosition(destination) instanceof ImmovableSolid)
            return false;
        return matrix.getCellAtPosition(destination).mass < mass;
    }

    protected void moveToPoint(GameMap matrix, Vector2 destinationVector2) {
        matrix.swapCellsAtPosition(this.position, destinationVector2);
    }

    public void updateDependingOnSpecialFeatures(GameMap matrix) {
        if (this instanceof Flammable) {
            ((Flammable) this).checkFlammability(matrix);
        }
    }

    public void ageIfPossible(GameMap matrix) {
        if (this instanceof Aging) {
            ((Aging) this).age(matrix);
        }
    }

    public boolean isBurning() {
        return isBurning;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void isRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
