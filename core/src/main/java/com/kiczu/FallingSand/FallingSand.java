package com.kiczu.FallingSand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
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


    public static int mapWidth = 32;
    public static int mapHeight = 18;

    public static float cellPixelSize;

    public static int screenWidth;
    public static int screenHeight;

    private FPSLogger fpsLogger;

    private ShapeRenderer shapeRenderer;
    public GameMap matrix;
    private OrthographicCamera camera;
    private Stage matrixStage;
    private Actor mapActor;

    private Viewport viewport;

    private InputManager inputManager;

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

        inputManager = new InputManager(matrix, camera, viewport, shapeRenderer);
        matrixStage = new Stage(viewport);
        mapActor = new MapActor(matrix, shapeRenderer);
        matrixStage.addActor(mapActor);


    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(0f, 1, 1, 1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 1);

        fpsLogger.log();
        matrix.updateAll();
        matrixStage.draw();
        inputManager.drawControlUIElements();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }
}