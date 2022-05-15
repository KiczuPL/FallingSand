package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;

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


}
