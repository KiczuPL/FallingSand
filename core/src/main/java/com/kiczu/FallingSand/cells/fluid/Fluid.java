package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public abstract class Fluid  extends Cell {

    public Fluid(Vector2 position){
        super(position);
    }
    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        Point destinationPointBottom = parentContainer.getPosition().getTranslatedCopy(0, -1);
        Point destinationPointBottomLeft = parentContainer.getPosition().getTranslatedCopy(-1, -1);
        Point destinationPointBottomRight = parentContainer.getPosition().getTranslatedCopy(1, -1);
        Point destinationPointRight = parentContainer.getPosition().getTranslatedCopy(1, 0);
        Point destinationPointLeft = parentContainer.getPosition().getTranslatedCopy(-1, 0);

        if (canMoveToPoint(matrix, parentContainer, destinationPointBottom)){
            moveToPoint(matrix,parentContainer,destinationPointBottom);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointBottomLeft) && canMoveToPoint(matrix, parentContainer, destinationPointLeft)){
            moveToPoint(matrix,parentContainer,destinationPointBottomLeft);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointBottomRight) && canMoveToPoint(matrix, parentContainer, destinationPointRight)) {
            moveToPoint(matrix,parentContainer,destinationPointBottomRight);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointRight)) {
            moveToPoint(matrix,parentContainer,destinationPointRight);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointLeft)) {
            moveToPoint(matrix,parentContainer,destinationPointLeft);
        }
    }
}
