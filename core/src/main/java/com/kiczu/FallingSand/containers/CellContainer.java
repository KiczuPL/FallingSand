package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.utils.Point;

public class CellContainer {

    private Cell cell;
    private Point position;

    public CellContainer(Point position) {
        this.position = position;
        cell = EmptyCell.getInstance();
    }


    public void setCell(Cell cell) {
        this.cell = cell;
    }


    public void draw(ShapeRenderer shapeRenderer) {
        if (cell != null)
            shapeRenderer.setColor(cell.getColor());
        else
            shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(position.getX(), position.getY(), 1, 1);
    }
}
