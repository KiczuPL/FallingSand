package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.utils.Point;

public class CellContainer {

    private Cell physicalCell;
    private Point position;
    private boolean isUpdated = false;

    public CellContainer(Point position) {
        this.position = position;
        physicalCell = EmptyCell.getInstance();
    }

    public Cell getPhysicalCell() {
        return physicalCell;
    }

    public void setPhysicalCell(Cell physicalCell) {
        isUpdated = true;
        this.physicalCell = physicalCell;
    }


    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(physicalCell.getColor());

        shapeRenderer.rect(position.getX(), position.getY(), 1, 1);
    }

    public void update(GameMap gameMap) {
        //if (!isUpdated)
            physicalCell.update(gameMap, this);
        //isUpdated=true;
    }

    public Color getColor() {
        return physicalCell.getColor();
    }

    public Point getPosition() {
        return position;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void isUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public boolean canCellMoveHere(Cell cell) {
        if (this.physicalCell instanceof EmptyCell)
            return true;
        //if (isUpdated)
            //return false;
        return cell.getMass() > physicalCell.getMass();
    }
}
