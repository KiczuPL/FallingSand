package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Water extends Fluid {
    public Water(Vector2 position) {
        super(position);
        color = Color.BLUE;
        mass = 997f;
        dispersionFactor = 5f;
    }

}
