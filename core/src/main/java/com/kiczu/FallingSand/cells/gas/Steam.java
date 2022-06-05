package com.kiczu.FallingSand.cells.gas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.cells.solid.immovable.Ice;
import com.kiczu.FallingSand.containers.GameMap;

public class Steam extends  Gas implements HeatConductive {
    public Steam(Vector2 position) {
        super(position);
        mass = 1f;
        color = Color.CYAN;
        heatCapacity = 2000f;
        temperature = 101f;
    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        if(temperature<100f){
            Cell c = new Water(position);
            c.setTemperature(temperature);
            matrix.setCellAtPosition(position, c);
            isRemoved = true;
        }
    }
}
