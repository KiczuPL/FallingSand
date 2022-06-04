package com.kiczu.FallingSand.cells.solid.immovable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Wall extends ImmovableSolid {
    public Wall(Vector2 position) {
        super(position);
        color = Color.DARK_GRAY;
        mass = Float.MAX_VALUE;
    }
}
