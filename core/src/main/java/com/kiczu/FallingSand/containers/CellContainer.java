package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.EmptyCell;

public class CellContainer {

    private Cell physicalCell;
    private Vector2 position;
    private boolean isUpdated = false;

    public CellContainer(Vector2 position) {
        this.position = position;
        physicalCell = EmptyCell.getInstance();
    }

    public Cell getPhysicalCell() {
        return physicalCell;
    }

    public void setPhysicalCell(Cell physicalCell) {
        isUpdated = true;
        this.physicalCell = physicalCell;
        physicalCell.setPosition(this.position.cpy());
    }


    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(physicalCell.getColor());

        shapeRenderer.rect(position.x, position.y, 1, 1);
    }

    public void update(GameMap gameMap) {
            physicalCell.update(gameMap, this);
    }

    public Color getColor() {
        return physicalCell.getColor();
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void isUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated; physicalCell.isUpdated(isUpdated);
    }

    public boolean canCellMoveHere(Cell cell) {
        if (this.physicalCell instanceof EmptyCell)
            return true;
        //if (isUpdated)
            //return false;
        return cell.getMass() > physicalCell.getMass();
    }
}
