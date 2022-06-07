package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.gas.Steam;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.cells.solid.immovable.Ice;
import com.kiczu.FallingSand.containers.GameMap;

public class Water extends Fluid implements HeatConductive {
    public Water(Vector2 position) {
        super(position);
        color = Color.BLUE;
        mass = 997f;
        dispersionFactor = 5f;
        temperature = 21f;
        heatCapacity = 4200f;
        heatConductivityFactor = 0.9f;
    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        if (temperature >= 100f) {
            Cell c = new Steam(position);
            c.setTemperature(temperature);
            matrix.setCellAtPosition(position, c);
            isRemoved = true;
        }else if (temperature < 0f) {
            Cell c = new Ice(position);
            c.setTemperature(temperature);
            matrix.setCellAtPosition(position, c);
            isRemoved = true;
        }
    }
}
