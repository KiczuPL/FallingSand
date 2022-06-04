package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.solid.movable.MovableSolid;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;
import com.kiczu.FallingSand.utils.RandomGenerator;

import java.util.List;

public abstract class Fluid extends Cell {
    protected float dispersionFactor;

    public Fluid(Vector2 position) {
        super(position);
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        if (isUpdated)
            return;
        isUpdated = true;

        velocity.x *=0.95f;
        Vector2 desiredPosition = position.cpy().add(velocity);
        Vector2 neighbourBelow = position.cpy().sub(0, 1);
        if(collidedLastFrame) {
            if (matrix.getCellAtPosition(neighbourBelow).getMass() >= mass) {
                float disp = RandomGenerator.getBoolean() ? -dispersionFactor : dispersionFactor;
                desiredPosition = position.cpy().add(disp, 0);
                if (!canMoveToPosition(matrix, desiredPosition))
                    desiredPosition = position.cpy().sub( disp, 0);
                velocity.x = desiredPosition.x - position.x;
                velocity.y = desiredPosition.y - position.y;
            }

            collidedLastFrame = false;
        }

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
                    velocity.x = 0;
                    velocity.y = 0;
                    collidedLastFrame = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!position.epsilonEquals(lastValidPosition)) {
            moveToPoint(matrix, lastValidPosition);
            movedInLastFrame = true;
        } else {
            movedInLastFrame = false;
        }
        Gravity.applyGravity(this);
    }

    private void reactToNeighbours(GameMap matrix) {
        List<Cell> neighbours = matrix.getAllNeighbours(position);
        for (Cell neighbour : neighbours)
            processNeighbour(matrix, neighbour);
    }

    private void processNeighbour(GameMap matrix, Cell neighbour) {
        if (neighbour instanceof MovableSolid) {
            MovableSolid n = ((MovableSolid) neighbour);
            if (movedInLastFrame)
                if (RandomGenerator.getBoolean(1f - n.getSettleProbability())) {
                    n.unSettle();
                }
        }
    }

}
