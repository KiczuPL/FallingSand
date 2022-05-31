package com.kiczu.FallingSand.utils;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;

public abstract class Gravity {
    public static final float x = 0f;
    public static final float y = -1f;

    public static void applyGravity(Cell cell) {
        Vector2 speed = cell.getVelocity();
        speed.add(x, y);
    }

    public static Vector2 getVector() {
        return new Vector2(x, y);
    }


}
