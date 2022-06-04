package com.kiczu.FallingSand.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.CellType;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.ui.BrushActor;

public class InputManager {
    private GameMap matrix;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage inputStage;

    private Brush brush;
    private CellType selectedCellType;

    private int brushSize;

    private BrushInputProcessor brushInputProcessor;

    public InputManager(GameMap matrix, OrthographicCamera camera, Viewport viewport, ShapeRenderer shapeRenderer) {
        this.matrix = matrix;
        this.camera = camera;
        this.viewport = viewport;
        brushSize = 6;
        brush = new Brush(this);
        selectedCellType = CellType.SAND;

        inputStage = new Stage(viewport);
        inputStage.addActor(new BrushActor(brush, shapeRenderer));


        brushInputProcessor = new BrushInputProcessor(this);


        Gdx.input.setInputProcessor(brushInputProcessor);
    }

    public void paintWithBrush() {
        int brushSize2 = 2 * brushSize;
        Vector2 brush = getBrushPosition().sub(brushSize, brushSize);
        Vector2 pos = new Vector2();
        for (int i = 0; i < brushSize2; i++)
            for (int j = 0; j < brushSize2; j++) {
                pos.x = brush.x + i;
                pos.y = brush.y + j;
                matrix.spawnCellAtPosition(pos, selectedCellType);
            }
    }

    public void eraseWithBrush() {
        int brushSize2 = 2 * brushSize;
        Vector2 brush = getBrushPosition().sub(brushSize, brushSize);
        Vector2 pos = new Vector2();
        for (int i = 0; i < brushSize2; i++)
            for (int j = 0; j < brushSize2; j++) {
                pos.x = brush.x + i;
                pos.y = brush.y + j;
                matrix.eraseCellAtPosition(pos);
            }
    }

    public void switchType() {
        if (selectedCellType == CellType.WATER) {
            selectedCellType = CellType.SAND;
        } else
            selectedCellType = CellType.WATER;
    }

    public void changeBrushSize(float c) {
        if (brushSize - c > 0) {
            brushSize -= c;
        }
    }

    public Vector2 getBrushPosition() {
        Vector3 position = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        return new Vector2(position.x, position.y);
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void drawBrush() {
        inputStage.draw();
    }

}
