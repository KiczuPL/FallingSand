package com.kiczu.FallingSand.cells.solid.immovable;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Wood extends ImmovableSolid {

    public Wood(Vector2 position) {
        super(position);
        color = Color.ORANGE;
        mass = 2000f;
    }
}
