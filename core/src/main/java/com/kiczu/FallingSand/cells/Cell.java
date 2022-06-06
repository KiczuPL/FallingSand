package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.interfaces.Aging;
import com.kiczu.FallingSand.cells.interfaces.Flammable;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.cells.solid.immovable.ImmovableSolid;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

import java.util.List;

public abstract class Cell {


    protected Vector2 position;
    protected Vector2 velocity;
    protected Color color;
    protected float mass;

    protected float heatCapacity;
    protected float temperature;
    protected float heatConductivityFactor;
    protected boolean isHeatUpdated;

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
        isHeatUpdated = false;
        maxHitPoints = 100;
        hitPoints = maxHitPoints;
        flammabilityResistance = 1f;
        temperature = 21f;
        heatCapacity = 1f;
        heatConductivityFactor = 1f;
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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void isUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
        isHeatUpdated = isUpdated;
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
        if (this instanceof Flammable)
            ((Flammable) this).checkFlammability(matrix);
        if (this instanceof HeatConductive)
            ((HeatConductive) this).updateHeat(matrix);
    }

    public void exchangeHeat(GameMap matrix) {
        if (isHeatUpdated)
            return;
        isHeatUpdated = true;
        List<Cell> neighbours = matrix.getAllNeighbours(position);
        float tempDifferenceSum = 0f;
        int conductiveNeighbours = 0;
        for (Cell neighbour : neighbours) {
            if (neighbour instanceof HeatConductive && !neighbour.isHeatUpdated) {
                tempDifferenceSum += Math.abs(temperature - neighbour.temperature);
                conductiveNeighbours++;
            }
        }
        if (conductiveNeighbours == 0 || tempDifferenceSum == 0f)
            return;

        float tempDifferenceAvg = tempDifferenceSum / (float) conductiveNeighbours;

        float tempDifference = 0f;

        for (Cell neighbour : neighbours) {
            if (neighbour instanceof HeatConductive && !neighbour.isHeatUpdated) {
                float temp = neighbour.getTemperature();
                float factor = heatConductivityFactor * neighbour.heatConductivityFactor;
                neighbour.temperature += factor * (((temperature - temp) - tempDifferenceAvg) / tempDifferenceAvg) * (heatCapacity / neighbour.heatCapacity);
                tempDifference += factor * (((temperature - temp) - tempDifferenceAvg) / tempDifferenceAvg) * (neighbour.heatCapacity / heatCapacity);
                neighbour.isHeatUpdated = true;
            }
        }
        this.temperature -= tempDifference;

    }


    public void ageIfPossible(GameMap matrix) {
        if (this instanceof Aging)
            ((Aging) this).age(matrix);
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
