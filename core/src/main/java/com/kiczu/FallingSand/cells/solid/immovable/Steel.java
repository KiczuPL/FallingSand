package com.kiczu.FallingSand.cells.solid.immovable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.containers.GameMap;

public class Steel extends ImmovableSolid implements HeatConductive {

    public Steel(Vector2 position) {
        super(position);
        color = Color.DARK_GRAY;
        mass = 7860f;

        heatCapacity = 1452;
        heatConductivityFactor = 0.95f;

    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        color = color.cpy().add(temperature-21f,0,0,0);
    }
}
