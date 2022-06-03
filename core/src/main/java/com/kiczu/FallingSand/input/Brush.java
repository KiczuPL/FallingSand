package com.kiczu.FallingSand.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Brush {

    private InputManager inputManager;

    public Brush(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        int size = inputManager.getBrushSize();
        Vector2 position = inputManager.getBrushPosition();
        position.x = (float) Math.floor(position.x);
        position.y = (float) Math.floor(position.y);
        System.out.println(position);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(position.x - size, position.y - size, 2 * size, 2 * size);
        shapeRenderer.end();
    }
}
