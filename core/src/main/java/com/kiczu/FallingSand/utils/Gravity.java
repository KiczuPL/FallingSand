package com.kiczu.FallingSand.utils;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;

public abstract class Gravity {


    public static void applyGravity(Cell cell){
        Vector2 speed = cell.getSpeed();
        speed.add(0,-2f);

    }

}
