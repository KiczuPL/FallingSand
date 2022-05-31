package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;

public abstract class Fluid extends Cell {

    public Fluid(Vector2 position) {
        super(position);
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        if (isUpdated)
            return;
        isUpdated = true;
        Gravity.applyGravity(this);

        Vector2 desiredPosition = position.cpy().add(velocity);
        Vector2 lastValidPosition = position.cpy();
        Vector2 currentPosition = position.cpy();

        int posX = (int) currentPosition.x;
        int posY = (int) currentPosition.y;

        int targetX = (int) desiredPosition.x;
        int targetY = (int) desiredPosition.y;
        int deltaX = (int) (desiredPosition.x - position.x);
        int deltaY = (int) (desiredPosition.y - position.y);


        boolean isDeltaXBigger = Math.abs(deltaX) >= Math.abs(deltaY);
        int signX = deltaX < 0 ? -1 : 1;
        int signY = deltaY < 0 ? -1 : 1;
        if (isDeltaXBigger) {
            float slope = (float) deltaY / (float) deltaX;
            for (int i = signX; Math.abs(i) < Math.abs(deltaX); i += signX) {
                int value = (int) (slope * i);
                currentPosition.x = posX + i;
                currentPosition.y = posY + value;
                if (matrix.isPointInBounds(currentPosition)) {
                    if (isPositionEmpty(matrix, currentPosition)) {
                        lastValidPosition = currentPosition.cpy();
                    } else {
                        processCollision(currentPosition, lastValidPosition, matrix);
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            float slope = (float) deltaX / (float) deltaY;
            for (int i = signY; Math.abs(i) < Math.abs(deltaY); i += signY) {
                int value = (int) (slope * i);
                currentPosition.x = posX + value;
                currentPosition.y = posY + i;
                if (matrix.isPointInBounds(currentPosition)) {
                    if (isPositionEmpty(matrix, currentPosition)) {
                        lastValidPosition = currentPosition.cpy();
                    } else {
                        processCollision(currentPosition, lastValidPosition, matrix);
                        break;
                    }
                } else {
                    break;
                }
            }
        }//TODO: Trzeba dodać przekierowanie prędkości po zderzeniu (znormalizować dwa wektory, odjąć od siebie i wtedy wymnożyć?)
        if (!position.epsilonEquals(lastValidPosition))
            moveToPoint(matrix, lastValidPosition);

    }

    private void processCollision(Vector2 neighbourPosition, Vector2 lastValidPosition, GameMap matrix) {

    }

}
