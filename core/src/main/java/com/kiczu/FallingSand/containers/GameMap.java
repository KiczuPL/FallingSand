package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.solid.movable.Sand;
import com.kiczu.FallingSand.utils.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMap {
    private int worldWidth;
    private int worldHeight;

    private int chunkSize;

    private ArrayList<Integer> indexes;

    private List<CellContainer> containers;


    public GameMap(int chunkSize, int horizontalChunks, int verticalChunks) {
        worldWidth = chunkSize * horizontalChunks;
        worldHeight = chunkSize * verticalChunks;
        this.chunkSize = chunkSize;
        int size = worldHeight * worldWidth;

        containers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            containers.add(new CellContainer(getPointFromIndex(i)));
        }

        for (int i = 0; i < size; i += worldWidth / 2) {
            containers.get(i).setPhysicalCell(new Sand(new Vector2(i - i / worldWidth * worldWidth,i / worldWidth)));
        }
        for (int i = worldHeight / 2; i < worldHeight - 3; i++) {
            for (int j = worldWidth / 2; j < worldWidth - 3; j++) {
                containers.get(i * worldWidth + j).setPhysicalCell(new Water(new Vector2(i,j)));
            }
        }
        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

    }

    public Point getPointFromIndex(int index) {
        int tmp = index / worldWidth;
        return new Point(index - tmp * worldWidth, tmp);
    }

    public int getIndexFromPoint(Point p) {
        return p.getY() * worldWidth + p.getX();
    }

    public CellContainer getCellContainer(Point p) {
        return containers.get(getIndexFromPoint(p));
    }

    public boolean isPointInBounds(Point p) {
        if (p.getX() < 0 || p.getX() > worldWidth)
            return false;
        if (p.getY() < 0 || p.getY() > worldWidth)
            return false;
        return true;
    }


    public void swapCellContainersContents(CellContainer source, CellContainer destination) {
        Cell tmp = source.getPhysicalCell();
        source.setPhysicalCell(destination.getPhysicalCell());
        destination.setPhysicalCell(tmp);
    }


    public void drawAll(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (CellContainer container : containers) {

            shapeRenderer.setColor(container.getColor());
            Point position = container.getPosition();

            shapeRenderer.rect(position.getX(), position.getY(), 10, 10);

            //container.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void iterateAndUpdate(Cell cell){

    }

    public void updateAll() {
        Collections.shuffle(indexes);
/*
        for (CellContainer c : containers) {
            c.update(this);
        }
        for (CellContainer c : containers) {
            c.isUpdated(false);
        }
*/
        for (Integer i : indexes) {
            containers.get(i).update(this);
        }

        for (Integer i : indexes) {
            containers.get(i).isUpdated(false);
        }
    }
}
