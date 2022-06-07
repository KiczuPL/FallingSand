package com.kiczu.FallingSand.cells.solid.immovable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.containers.GameMap;

public class Steel extends ImmovableSolid implements HeatConductive {

    public Steel(Vector2 position) {
        super(position);
        color = new Color(0x444444ff);
        mass = 7860f;

        heatCapacity = 1452;
        heatConductivityFactor = 0.95f;

    }

    @Override
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
    }
}
