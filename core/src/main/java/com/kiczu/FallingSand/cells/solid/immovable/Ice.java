package com.kiczu.FallingSand.cells.solid.immovable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.containers.GameMap;

public class Ice extends ImmovableSolid implements HeatConductive {
    public Ice(Vector2 position){
        super(position);
        color = new Color(183, 180, 253,1);
        mass = 700f;
        temperature = -5f;
        heatCapacity = 3100f;
        heatConductivityFactor = 0.98f;
    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        if(temperature>0f){
            Cell c = new Water(position);
            c.setTemperature(temperature);
            matrix.setCellAtPosition(position, c);
            isRemoved = true;
        }
    }
}
