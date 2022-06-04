package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

public class EmptyCell extends Cell {
    private static Cell emptyCell;

    public EmptyCell(Vector2 position) {
        super(position);
        color = Color.BLACK;
        mass = -Float.MAX_VALUE;
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {

    }


}
