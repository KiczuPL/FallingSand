package com.kiczu.FallingSand.cells.solid.movable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Fluid;
import com.kiczu.FallingSand.cells.solid.immovable.ImmovableSolid;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Gravity;

public class MovableSolid extends Cell {

    public MovableSolid(Vector2 position) {
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

        boolean collisionProcedured = false;


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
                        collisionProcedured = collisionProcedured = processCollision(currentPosition, lastValidPosition, matrix);
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
                        collisionProcedured = processCollision(currentPosition, lastValidPosition, matrix);
                        break;
                    }
                } else {
                    break;
                }
            }
        }//TODO: Trzeba dodać przekierowanie prędkości po zderzeniu (znormalizować dwa wektory, odjąć od siebie i wtedy wymnożyć?)
        if (!position.epsilonEquals(lastValidPosition) && !collisionProcedured)
            moveToPoint(matrix, lastValidPosition);

    }

    private boolean processCollision(Vector2 neighbourPosition, Vector2 lastValidPosition, GameMap matrix) {
        /*if (velocity.len2() >= 1) {
            velocity.x = velocity.x * (-0.25f);
            velocity.y = velocity.y * (-0.25f);
        } else {
            velocity.x = 0;
            velocity.y = 0;
        }
        */
        Vector2 normalisedVelocity = velocity.cpy().nor();
        Cell neighbour = matrix.getCellAtPosition(neighbourPosition);

        if (neighbour instanceof MovableSolid) {
            if (neighbour.getMass() > getMass()) {
                matrix.swapCellsAtPosition(lastValidPosition, neighbourPosition);
                if(!position.epsilonEquals(neighbourPosition))
                    matrix.swapCellsAtPosition(position, lastValidPosition);
            } else {
                velocity.x = 0;
                velocity.y = 0;
            }

        } else if (neighbour instanceof ImmovableSolid) {

        } else if (neighbour instanceof Fluid) {
            if (neighbour.getMass() < getMass()) {
                matrix.swapCellsAtPosition(lastValidPosition, neighbourPosition);
                if(!position.epsilonEquals(neighbourPosition))
                matrix.swapCellsAtPosition(position, lastValidPosition);
            } else {
                velocity.x = 0;
                velocity.y = 0;
            }
        }
        return true;
    }


    private int getAdditionalCoordinate(float val) {
        if (val < -.1f) {
            return (int) Math.floor(val);
        } else if (val > .1f) {
            return (int) Math.ceil(val);
        } else {
            return 0;
        }
    }


}
