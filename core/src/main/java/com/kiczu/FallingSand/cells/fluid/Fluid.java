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
            for (int i = signX; i > deltaX; i += signX) {
                float slope = (float) deltaY / (float) deltaX;
                int value = (int) (slope * i);
                currentPosition.x = posX + i;
                currentPosition.y = posY + value;
                if (matrix.isPointInBounds(currentPosition)) {
                    if(canMoveToPoint(matrix,parentContainer,currentPosition)){
                        lastValidPosition = currentPosition.cpy();
                    }
                } else {
                    break;
                }
            }
        } else {
            for (int i = signY; i > deltaY; i += signY) {
                float slope = (float) deltaX / (float) deltaY;
                int value = (int) (slope * i);
                currentPosition.x = posY + i;
                currentPosition.y = posX + value;
                if (matrix.isPointInBounds(currentPosition)) {
                    if(canMoveToPoint(matrix,parentContainer,currentPosition)){
                        lastValidPosition = currentPosition.cpy();
                    }
                } else {
                    break;
                }
            }
        }

        moveToPoint(matrix, parentContainer, lastValidPosition);

    }

    private void iterateAndMove(int deltaX, int deltaY) {

    }

}
