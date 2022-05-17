package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public abstract class Cell {

    protected Color color;
    protected float density;


    public Color getColor() {
        return color;
    }

    public float getDensity(){
        return density;
    }

    public abstract void update(GameMap matrix, CellContainer parentContainer);

    protected boolean tryMoveToPoint(GameMap matrix, CellContainer parentContainer, Point destinationPoint) {
        if (matrix.isPointInBounds(destinationPoint)) {
            CellContainer destination = matrix.getCellContainer(destinationPoint);

            if (destination.canCellMoveHere(this)) {
                matrix.swapCellContainersContents(parentContainer, destination);
                return true;
            } else
                return false;
        }
        return false;
    }

}
