package com.kiczu.FallingSand.cells.solid.movable;

import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public class MovableSolid extends Cell {

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        Point destinationPointBottom = parentContainer.getPosition().getTranslatedCopy(0, -1);
        Point destinationPointBottomLeft = parentContainer.getPosition().getTranslatedCopy(-1, -1);
        Point destinationPointBottomRight = parentContainer.getPosition().getTranslatedCopy(1, -1);


        if (canMoveToPoint(matrix, parentContainer, destinationPointBottom)){
         moveToPoint(matrix,parentContainer,destinationPointBottom);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointBottomLeft)){
            moveToPoint(matrix,parentContainer,destinationPointBottomLeft);
        }
        else if (canMoveToPoint(matrix, parentContainer, destinationPointBottomRight)) {
            moveToPoint(matrix,parentContainer,destinationPointBottomRight);
        }

    }


}
