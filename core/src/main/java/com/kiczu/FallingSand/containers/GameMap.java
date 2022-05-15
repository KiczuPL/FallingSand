package com.kiczu.FallingSand.containers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kiczu.FallingSand.utils.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int worldWidth;
    private int worldHeight;

    private int chunkSize;

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
    }

    public Point getPointFromIndex(int index){
        int tmp = index/worldWidth;
        return new Point(tmp,tmp-index*worldWidth);
    }


    public void drawAll(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (CellContainer container : containers) {
            container.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void updateAll() {
    }
}
