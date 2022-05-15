package com.kiczu.FallingSand.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kiczu.FallingSand.containers.GameMap;

public class MapActor extends Actor {
    private GameMap matrix;
    private ShapeRenderer shapeRenderer;

    public MapActor(GameMap matrix, ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        this.matrix = matrix;
    }

    @Override
    public void draw(Batch batch, float prentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        matrix.drawAll(shapeRenderer);
        batch.begin();
    }
}
