package com.kiczu.FallingSand.cells.solid.movable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Sand extends MovableSolid {

    public Sand(Vector2 position) {
        super(position);
        color = Color.YELLOW;
        mass = 1442f;
        frictionFactor = 0.25f;
        settleProbability = 0.5f;
    }
}
