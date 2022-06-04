package com.kiczu.FallingSand.cells.solid.movable;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Fluid;
import com.kiczu.FallingSand.cells.solid.immovable.ImmovableSolid;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;
import com.kiczu.FallingSand.utils.RandomGenerator;

import java.util.List;

public class MovableSolid extends Cell {
    protected float frictionFactor;
    protected float settleProbability;
    protected boolean isSettled;



    public MovableSolid(Vector2 position) {
        super(position);
        collidedLastFrame = false;
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        if (isUpdated)
            return;
        isUpdated = true;


        Cell neighbourBelow = matrix.getCellAtPosition(position.cpy().sub(0, 1));
        Cell neighbourBelowRight = matrix.getCellAtPosition(position.cpy().sub(-1, 1));
        Cell neighbourBelowLeft = matrix.getCellAtPosition(position.cpy().sub(1, 1));

        if (neighbourBelow.getMass() < mass) {
            unSettle();
        } else {
            velocity.x *= frictionFactor;
        }

        if (collidedLastFrame) {
            if (neighbourBelow.getMass() < mass) {
                velocity.x = 0;
                velocity.y = -1;
                unSettle();
            } else if (RandomGenerator.getBoolean(settleProbability)) {
                settle();
            } else if (neighbourBelowRight.getMass() < mass || neighbourBelowLeft.getMass() < mass) {
                if (neighbourBelowRight.getMass() < mass && neighbourBelowLeft.getMass() < mass) {
                    if (RandomGenerator.getBoolean()) {
                        velocity.x = -1;
                        velocity.y = -1;
                    } else {
                        velocity.x = 1;
                        velocity.y = -1;
                    }
                } else if (neighbourBelowRight.getMass() < mass) {
                    velocity.x = 1;
                    velocity.y = -1;
                } else {
                    velocity.x = -1;
                    velocity.y = -1;
                }
                unSettle();
            } else {
                float factor = (RandomGenerator.getBoolean() ? 0.1f : -0.1f);
                velocity.x += velocity.y * factor;
            }
            collidedLastFrame = false;

        }
        if (isSettled)
            return;

        Vector2 desiredPosition = position.cpy().add(velocity);
        Vector2 lastValidPosition = position.cpy();
        Vector2 currentPosition = position.cpy();

        int posX = (int) currentPosition.x;
        int posY = (int) currentPosition.y;

        int deltaX = (int) (desiredPosition.x - position.x);
        int deltaY = (int) (desiredPosition.y - position.y);

        boolean isDeltaXBigger = Math.abs(deltaX) >= Math.abs(deltaY);

        int sign;
        float slope;
        int delta;

        if (isDeltaXBigger) {
            slope = (float) deltaY / (float) deltaX;
            sign = deltaX < 0 ? -1 : 1;
            delta = deltaX;
        } else {
            slope = (float) deltaX / (float) deltaY;
            sign = deltaY < 0 ? -1 : 1;
            delta = deltaY;
        }

        for (int i = sign; Math.abs(i) <= Math.abs(delta); i += sign) {
            int value = Math.round((slope * i));

            if (isDeltaXBigger) {
                currentPosition.x = posX + i;
                currentPosition.y = posY + value;
            } else {
                currentPosition.x = posX + value;
                currentPosition.y = posY + i;
            }
            if (matrix.isPointInBounds(currentPosition)) {
                if (canMoveToPosition(matrix, currentPosition)) {
                    lastValidPosition = currentPosition.cpy();
                    reactToNeighbours(matrix);
                } else {
                    collision();
                    break;
                }
            } else {
                break;
            }
        }

        if (!position.epsilonEquals(lastValidPosition)) {
            moveToPoint(matrix, lastValidPosition);
            movedInLastFrame=true;
        } else {
            if (!movedInLastFrame) {
                settle();
            } else
                movedInLastFrame = false;
        }

        Gravity.applyGravity(this);
    }

    public void settle() {
        velocity.x = 0;
        velocity.y = 0;
        isSettled = true;
    }

    public void unSettle() {
        isSettled = false;
    }

    public float getSettleProbability(){
        return settleProbability;
    }

    private void collision() {
        collidedLastFrame = true;
        //velocity.x = 0;
        //velocity.y = 0;
    }

    private void reactToNeighbours(GameMap matrix) {
        List<Cell> neighbours = matrix.getAllNeighbours(position);
        for (Cell neighbour : neighbours)
            processNeighbour(matrix, neighbour);
    }

    private void processNeighbour(GameMap matrix, Cell neighbour) {
        if (neighbour instanceof MovableSolid) {
            MovableSolid n = ((MovableSolid) neighbour);
            if(movedInLastFrame)
            if (RandomGenerator.getBoolean(1f - n.settleProbability)) {
                n.unSettle();
            }
        }
    }


}
