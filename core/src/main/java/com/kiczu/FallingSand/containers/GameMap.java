package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.solid.immovable.Wood;
import com.kiczu.FallingSand.cells.solid.movable.Sand;

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


        for (int i = worldHeight / 2; i < worldHeight - 20; i++) {
            for (int j = worldWidth / 2; j < worldWidth - 50; j++) {
                Vector2 v = new Vector2(j, i);
                setCellAtPosition(v, new Sand(v));
            }
        }

        for (int i = 0; i < worldHeight / 2 - 20; i++) {
            for (int j = worldWidth / 2; j < worldWidth - 50; j++) {
                Vector2 v = new Vector2(j, i);
                setCellAtPosition(v, new Water(v));
            }
        }

        for (int i = 0; i < worldHeight - 100; i++) {
            Vector2 v = new Vector2(worldWidth / 3, i);
            setCellAtPosition(v, new Wood(v));

        }


        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

    }

    public Vector2 getPointFromIndex(int index) {
        int tmp = index / worldWidth;
        return new Vector2(index - tmp * worldWidth, tmp);
    }

    public int getIndexFromPoint(Vector2 p) {
        return (int) p.y * worldWidth + (int) p.x;
    }

    public CellContainer getCellContainer(Vector2 p) {
        return containers.get(getIndexFromPoint(p));
    }

    public boolean isPointInBounds(Vector2 p) {
        if (p.x < 0 || p.x >= worldWidth)
            return false;
        if (p.y < 0 || p.y >= worldHeight)
            return false;
        return true;
    }

    public void setCellAtPosition(Vector2 position, Cell cell) {
        getCellContainer(position).setPhysicalCell(cell);
    }

    public Cell getCellAtPosition(Vector2 position) {
        return getCellContainer(position).getPhysicalCell();
    }

    public void swapCellsAtPosition(Vector2 source, Vector2 target) {
        CellContainer s = getCellContainer(source);
        CellContainer t = getCellContainer(target);
        Cell tmp = s.getPhysicalCell();
        s.setPhysicalCell(t.getPhysicalCell());
        t.setPhysicalCell(tmp);
    }


    public void drawAll(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (CellContainer container : containers) {

            shapeRenderer.setColor(container.getColor());
            Vector2 position = container.getPosition();

            shapeRenderer.rect(position.x, position.y, 10, 10);

            //container.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void iterateAndUpdate(Cell cell) {

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
