package com.kiczu.FallingSand.cells.gas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.cells.interfaces.Aging;
import com.kiczu.FallingSand.containers.GameMap;

public class Fire extends Gas implements Aging {


    public Fire(Vector2 position) {
        super(position);
        mass = 0f;
        color = Color.YELLOW;
        lifeSpan = 40;
    }


    @Override
    public void die(GameMap matrix) {
        matrix.setCellAtPosition(position, EmptyCell.getInstance());
    }

    @Override
    public void age(GameMap matrix) {
        lifeSpan--;
        if (lifeSpan == 25) {
            color = Color.RED;
        }

        if (lifeSpan == 0) {
            die(matrix);
        }
    }
}
