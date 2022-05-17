package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

public class EmptyCell extends Cell {
    private static Cell emptyCell;

    private EmptyCell() {
        color = Color.BLACK;
    }

    public static Cell getInstance() {
        if (emptyCell == null) {
            emptyCell = new EmptyCell();
        }
        return emptyCell;
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer){

    }


}
