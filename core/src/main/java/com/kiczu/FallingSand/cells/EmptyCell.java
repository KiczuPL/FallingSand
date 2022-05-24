package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.Point;

public class EmptyCell extends Cell {
    private static Cell emptyCell;

    private EmptyCell() {
        super(new Vector2(-1f,-1f));
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
