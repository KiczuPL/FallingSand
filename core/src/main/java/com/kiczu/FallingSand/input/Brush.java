package com.kiczu.FallingSand.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.FallingSand;

public class Brush {

    private InputManager inputManager;

    public Brush(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        int size = inputManager.getBrushPixelSize();
        int brushSize2 = 2 * size - (int)FallingSand.cellPixelSize;
        Vector2 position = inputManager.getBrushPositionOnMap();
        //position.x = (float) Math.floor(position.x);
        //position.y = (float) Math.floor(position.y);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(position.x-size , position.y-size , brushSize2, brushSize2);
        shapeRenderer.end();
    }
}
