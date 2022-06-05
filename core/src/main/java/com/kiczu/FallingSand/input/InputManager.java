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
    private int sel = 0;

    private Vector2 lastFrameBrushPosition;

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
        // Vector2 pos = new Vector2();
        // for (int i = 0; i < brushSize2; i++)
        //     for (int j = 0; j < brushSize2; j++) {
        //        pos.x = brush.x + i;
        //        pos.y = brush.y + j;
        //        matrix.spawnCellAtPosition(pos, selectedCellType);
        //    }
        //System.out.println(matrix.getCellAtPosition(pos).getTemperature());
        if (lastFrameBrushPosition == null) {
            lastFrameBrushPosition = getBrushPosition();
        }

        Vector2 currentPosition = lastFrameBrushPosition;
        Vector2 desiredPosition = getBrushPosition();

        int posX = (int) currentPosition.x;
        int posY = (int) currentPosition.y;

        int deltaX = (int) (desiredPosition.x - currentPosition.x);
        int deltaY = (int) (desiredPosition.y - currentPosition.y);

        boolean isDeltaXBigger = Math.abs(deltaX) >= Math.abs(deltaY);

        int sign;
        float slope;
        int delta;

        if (isDeltaXBigger) {
            slope = (float) deltaY / (float) deltaX;
            sign = deltaX < 0 ? -1 : 1;
            delta = deltaX;
        } else {
            slope = (float) deltaX / (float) deltaY;
            sign = deltaY < 0 ? -1 : 1;
            delta = deltaY;
        }

        for (int i = 0; Math.abs(i) <= Math.abs(delta); i += sign) {
            int value = Math.round((slope * i));

            if (isDeltaXBigger) {
                currentPosition.x = posX + i;
                currentPosition.y = posY + value;
            } else {
                currentPosition.x = posX + value;
                currentPosition.y = posY + i;
            }
            Vector2 pos = new Vector2();
            for (int r = 0; r < brushSize2; r++)
                for (int c = 0; c < brushSize2; c++) {
                    pos.x = currentPosition.x + r - brushSize;
                    pos.y = currentPosition.y + c - brushSize;
                    matrix.spawnCellAtPosition(pos, selectedCellType);
                }
        }
        lastFrameBrushPosition = currentPosition;
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
        CellType[] t = new CellType[5];
        t[0] = CellType.SAND;
        t[1] = CellType.WATER;
        t[2] = CellType.WOOD;
        t[3] = CellType.FIRE;
        t[4] = CellType.COLD_FIRE;
        if (sel + 1 < t.length) {
            sel++;
        } else {
            sel = 0;
        }
        selectedCellType = t[sel];
    }

    public void changeBrushSize(float c) {
        if (brushSize - c > 0) {
            if (c < 0) {
                brushSize++;
            } else {
                brushSize--;
            }
        }
    }

    public void untouchBrush(){
        lastFrameBrushPosition = null;
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
