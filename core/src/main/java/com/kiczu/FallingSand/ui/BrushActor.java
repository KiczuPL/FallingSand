package com.kiczu.FallingSand.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kiczu.FallingSand.input.Brush;

public class BrushActor extends Actor {
    private ShapeRenderer shapeRenderer;
    private Brush brush;

    public BrushActor(Brush brush, ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        this.brush=brush;
    }

    @Override
    public void draw(Batch batch, float prentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        brush.draw(shapeRenderer);
        batch.begin();
    }
}
