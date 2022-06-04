package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.CellType;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.solid.immovable.Wall;
import com.kiczu.FallingSand.cells.solid.immovable.Wood;
import com.kiczu.FallingSand.cells.solid.movable.Sand;
import com.kiczu.FallingSand.utils.RandomGenerator;

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
                //setCellAtPosition(v, new Sand(v));
            }
        }

        for (int i = 5; i < worldHeight / 2 - 20; i++) {
            for (int j = worldWidth / 2; j < worldWidth - worldHeight / 4; j++) {
                Vector2 v = new Vector2(j, i);
                //setCellAtPosition(v, new Water(v));
            }
        }
        for (int i = 0; i < worldWidth - worldWidth / 3; i++) {
            Vector2 v = new Vector2(i, worldHeight / 2);
            setCellAtPosition(v, new Wood(v));

        }
        Vector2 v = new Vector2(worldWidth / 4, worldHeight / 5 * 4);
        setCellAtPosition(v, new Water(v));


        setUpWalls();
        indexes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }

    }


    private void setUpWalls() {
        Vector2 v = new Vector2(0, 0);
        for (int i = 0; i < worldWidth; i++) {
            v.x = i;
            v.y = 0;
            setCellAtPosition(v, new Wall(v));
        }

        for (int i = 0; i < worldWidth; i++) {
            v.x = i;
            v.y = worldHeight - 1;
            setCellAtPosition(v, new Wall(v));
        }


        for (int i = 0; i < worldHeight; i++) {
            v.x = 0;
            v.y = i;
            setCellAtPosition(v, new Wall(v));
        }

        for (int i = 0; i < worldHeight; i++) {
            v.x = worldWidth - 1;
            v.y = i;
            setCellAtPosition(v, new Wall(v));
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
        if (p.x < 1 || p.x >= worldWidth - 1)
            return false;
        if (p.y < 1 || p.y >= worldHeight - 1)
            return false;
        return true;
    }

    public List<Cell> getAllNeighbours(Vector2 position) {
        List<Cell> result = new ArrayList<>();
        Cell n;

        n = getNeighbour(position, -1f, -1f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, -1f, 0f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, -1f, 1f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, 0f, -1f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, 0f, 1f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, 1f, -1f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, 1f, 0f);
        if (n != null)
            result.add(n);
        n = getNeighbour(position, 1f, 1f);
        if (n != null)
            result.add(n);

        return result;
    }

    public Cell getNeighbour(Vector2 position, float dx, float dy) {
        Vector2 pos = position.cpy().add(dx, dy);
        if (!isPointInBounds(pos))
            return null;
        return getCellAtPosition(pos);
    }

    public void spawnCellAtPosition(Vector2 position, CellType cellType) {
        if (isPointInBounds(position) && getCellAtPosition(position) instanceof EmptyCell) {
            setCellAtPosition(position, cellType.create(position));
        }
    }

    public void eraseCellAtPosition(Vector2 position) {
        if (isPointInBounds(position)) {
            setCellAtPosition(position, EmptyCell.getInstance());
        }
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

        }
        shapeRenderer.end();
    }

    public void updateAll() {
        Collections.shuffle(indexes, RandomGenerator.getInstance());

        for (Integer i : indexes) {
            containers.get(i).update(this);
        }

        for (Integer i : indexes) {
            containers.get(i).isUpdated(false);
        }
    }
}
