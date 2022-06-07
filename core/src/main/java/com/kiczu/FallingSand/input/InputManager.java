package com.kiczu.FallingSand.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kiczu.FallingSand.FallingSand;
import com.kiczu.FallingSand.cells.CellType;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.ui.BrushActor;
import com.kiczu.FallingSand.ui.ControlMenu;

public class InputManager {
    private FallingSand game;
    private GameMap matrix;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage inputStage;

    private ControlMenu controlMenu;
    private boolean isControlMenuActive;

    private Brush brush;
    private CellType selectedCellType;
    private int sel = 0;

    private Vector2 lastFrameBrushPosition;

    private int brushSize;

    private BrushInputProcessor brushInputProcessor;

    public InputManager(FallingSand game, GameMap matrix, OrthographicCamera camera, Viewport viewport, ShapeRenderer shapeRenderer) {
        this.game = game;
        isControlMenuActive = false;
        this.matrix = matrix;
        this.camera = camera;
        this.viewport = viewport;
        brushSize = 6;
        brush = new Brush(this);
        selectedCellType = CellType.SAND;

        inputStage = new Stage(viewport);
        inputStage.addActor(new BrushActor(brush, shapeRenderer));

        controlMenu = new ControlMenu(this, viewport);

        brushInputProcessor = new BrushInputProcessor(this);


        Gdx.input.setInputProcessor(brushInputProcessor);
    }

    public void paintWithBrush() {
        int brushSize2 = 2 * brushSize - 1;
        if (lastFrameBrushPosition == null) {
            lastFrameBrushPosition = getBrushPositionOnMap().scl(1 / FallingSand.cellPixelSize);
        }

        Vector2 currentPosition = lastFrameBrushPosition;
        Vector2 desiredPosition = getBrushPositionOnMap().scl(1 / FallingSand.cellPixelSize);

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

    public void setSelectedCellType(CellType cellType) {
        selectedCellType = cellType;
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

    public void unTouchBrush() {
        lastFrameBrushPosition = null;
    }

    public Vector2 getBrushPositionOnMap() {
        Vector3 position = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x -= position.x % FallingSand.cellPixelSize - FallingSand.cellPixelSize;
        position.y -= position.y % FallingSand.cellPixelSize - FallingSand.cellPixelSize;


        Vector2 v = new Vector2(position.x, position.y).scl(1 / FallingSand.cellPixelSize).sub(1, 1);
       // if (matrix.isPointInBounds(v))
        //    System.out.println("" + matrix.getCellAtPosition(v).getClass() + " : " + matrix.getCellAtPosition(v).getTemperature());

        return new Vector2(position.x, position.y);
    }


    public int getBrushPixelSize() {
        return brushSize * (int) FallingSand.cellPixelSize;
    }

    public void drawControlUIElements() {
        if (isControlMenuActive) {
            controlMenu.draw();
        } else {
            inputStage.draw();
        }
    }

    public void setMenuActive() {
        isControlMenuActive = true;
        controlMenu.setPosition(getBrushPositionOnMap());
        Gdx.input.setInputProcessor(controlMenu.getInputProcessor());
    }

    public void closeMenu() {
        isControlMenuActive = false;
        Gdx.input.setInputProcessor(brushInputProcessor);
    }

    public void toggleRunning() {
        game.isRunning(!game.isRunning());
    }

    public void increaseGameSpeed() {
        System.out.println(">");
        game.increaseSpeed();
    }

    public void decreaseGameSpeed() {
        System.out.println("<");
        game.decreaseSpeed();
    }
}
