package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.utils.Point;

public class CellContainer {

    private Cell physicalCell;
    private Point position;

    public CellContainer(Point position) {
        this.position = position;
        physicalCell = EmptyCell.getInstance();
    }

    public Cell getPhysicalCell() {
        return physicalCell;
    }

    public void setPhysicalCell(Cell physicalCell) {
        this.physicalCell = physicalCell;
    }


    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(physicalCell.getColor());

        shapeRenderer.rect(position.getX(), position.getY(), 1, 1);
    }

    public void update(GameMap gameMap) {
        physicalCell.update(gameMap, this);
    }

    public Color getColor() {
        return physicalCell.getColor();
    }

    public Point getPosition() {
        return position;
    }

    public boolean canCellMoveHere(Cell cell) {
        if (this.physicalCell instanceof EmptyCell)
            return true;
        return cell.getDensity() > physicalCell.getDensity();
    }
}
