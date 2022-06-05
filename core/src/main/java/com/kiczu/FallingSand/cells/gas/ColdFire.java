package com.kiczu.FallingSand.cells.gas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.cells.interfaces.Aging;
import com.kiczu.FallingSand.cells.interfaces.Destructible;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.RandomGenerator;

public class ColdFire extends Gas implements Aging, Destructible, HeatConductive {


    public ColdFire(Vector2 position) {
        super(position);
        mass = 1f;
        color = Color.WHITE;
        lifeSpan = RandomGenerator.getIntFromRange(20, 30);
        isBurning = true;
        heatCapacity =  100000f;
        temperature = -200f;
    }


    @Override
    public void die(GameMap matrix) {
        matrix.setCellAtPosition(this.position, new EmptyCell(position));
        isRemoved = true;
    }

    @Override
    public void age(GameMap matrix) {
        lifeSpan--;
        if (lifeSpan == 25) {
            color = Color.ROYAL;
        }

        if (lifeSpan == 0) {
            die(matrix);
        }
    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        if (temperature > -100f) {
            die(matrix);
        }
    }
}