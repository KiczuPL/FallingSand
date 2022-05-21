package com.kiczu.FallingSand.cells.solid.movable;

import com.badlogic.gdx.graphics.Color;
import com.kiczu.FallingSand.utils.Point;

public class Sand extends MovableSolid {

    public Sand(Point position) {
        super(position);
        color = Color.YELLOW;
        mass = 1442f;
    }
}
