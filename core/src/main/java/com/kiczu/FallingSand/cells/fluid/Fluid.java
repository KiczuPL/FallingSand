package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;
import com.kiczu.FallingSand.utils.RandomGenerator;

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


        Vector2 desiredPosition = position.cpy().add(velocity);
        Vector2 neighbourBelow = position.cpy().sub(0, 1);
        if (matrix.getCellAtPosition(neighbourBelow).getMass() >= mass) {
            desiredPosition = position.cpy().add(RandomGenerator.getBoolean() ? -dispersionFactor : dispersionFactor, 0);
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
                } else {
                    //collision();
                    break;
                }
            } else {
                break;
            }
        }
        if (!position.epsilonEquals(lastValidPosition))
            moveToPoint(matrix, lastValidPosition);
        Gravity.applyGravity(this);
    }

    private void collision() {
        collidedLastFrame = true;
        //velocity.x = 0;
        //velocity.y = 0;
    }

}
