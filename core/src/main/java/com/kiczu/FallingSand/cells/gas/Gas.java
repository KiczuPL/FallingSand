package com.kiczu.FallingSand.cells.gas;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;
import com.kiczu.FallingSand.utils.RandomGenerator;



public abstract class Gas extends Cell {
    public Gas(Vector2 position) {
        super(position);
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        if (isUpdated)
            return;
        isUpdated = true;


        updateDependingOnSpecialFeatures(matrix);
        if (isRemoved)
            return;


        float rand1 = RandomGenerator.getFromRange(-1, 3);
        float rand2 = RandomGenerator.getFromRange(-2, 5);
        velocity.x += rand1;
        velocity.y += rand2;

        collidedLastFrame = false;
        //}


        velocity.y *= 0.6f;
        velocity.x *= 0.6f;
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

        ageIfPossible(matrix);
        velocity.x -= Gravity.x;
        velocity.y -= Gravity.y;
    }

}
