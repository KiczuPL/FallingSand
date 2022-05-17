package com.kiczu.FallingSand.cells.fluid;

import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public abstract class Fluid  extends Cell {
    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        Point destinationPointBottom = parentContainer.getPosition().getTranslatedCopy(0, -1);
        Point destinationPointBottomLeft = parentContainer.getPosition().getTranslatedCopy(-1, -1);
        Point destinationPointBottomRight = parentContainer.getPosition().getTranslatedCopy(1, -1);
        Point destinationPointRight = parentContainer.getPosition().getTranslatedCopy(1, 0);
        Point destinationPointLeft = parentContainer.getPosition().getTranslatedCopy(-1, 0);
        if (tryMoveToPoint(matrix, parentContainer, destinationPointBottom)) ;
        else if (tryMoveToPoint(matrix, parentContainer, destinationPointBottomLeft)) ;
        else if (tryMoveToPoint(matrix, parentContainer, destinationPointBottomRight)) ;
        else if (tryMoveToPoint(matrix, parentContainer, destinationPointRight)) ;
        else if (tryMoveToPoint(matrix, parentContainer, destinationPointLeft)) ;
    }
}
