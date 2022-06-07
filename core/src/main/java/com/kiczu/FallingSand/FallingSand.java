package com.kiczu.FallingSand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.input.InputManager;
import com.kiczu.FallingSand.ui.MapActor;


public class FallingSand extends ApplicationAdapter {


    public static int mapWidth = 320;
    public static int mapHeight = 180;

    public static float cellPixelSize;

    public static int screenWidth;
    public static int screenHeight;

    private boolean isRunning = true;

    private FPSLogger fpsLogger;

    private ShapeRenderer shapeRenderer;
    public GameMap matrix;
    private OrthographicCamera camera;
    private Stage matrixStage;
    private Actor mapActor;

    private Viewport viewport;

    private InputManager inputManager;


    private long startTime;
    private long elapsedTime;
    private long speed = 10000000;

    @Override
    public void create() {
        super.create();

        camera = new OrthographicCamera();

        fpsLogger = new FPSLogger();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        cellPixelSize = (float) screenWidth / (float) mapWidth;

        matrix = new GameMap(mapWidth, mapHeight, cellPixelSize);


        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);


        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        viewport = new FitViewport(screenWidth, screenHeight, camera);


        matrixStage = new Stage(viewport);
        matrixStage.addActor(new MapActor(matrix, shapeRenderer));


        camera.position.set(screenWidth / 2, screenHeight / 2, 0);

        inputManager = new InputManager(this, matrix, camera, viewport, shapeRenderer);
        matrixStage = new Stage(viewport);
        mapActor = new MapActor(matrix, shapeRenderer);
        matrixStage.addActor(mapActor);

        startTime = 1;
        elapsedTime = 0;
    }

    @Override
    public void render() {
        startTime = System.nanoTime();
        Gdx.gl.glClearColor(0f, 1, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 1);

        //fpsLogger.log();

        System.out.println(speed);
        if (isRunning && elapsedTime >= speed) {
            matrix.updateAll();
            elapsedTime = 0;
        }


        matrixStage.draw();
        inputManager.drawControlUIElements();
        elapsedTime += (System.nanoTime() - startTime);
    }

    public void increaseSpeed() {
        if (speed < 20000000) {
            speed += 2000000;
        }
    }

    public void decreaseSpeed() {
        if (speed >= 10000000) {
            speed -= 2000000;
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }

    public void isRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }
}