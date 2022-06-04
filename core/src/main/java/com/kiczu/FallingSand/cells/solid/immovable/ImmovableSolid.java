package com.kiczu.FallingSand.cells.solid.immovable;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.containers.CellContainer;
import com.kiczu.FallingSand.containers.GameMap;

public abstract class ImmovableSolid extends Cell {
    public ImmovableSolid(Vector2 position) {
        super(position);
    }

    @Override
    public void update(GameMap matrix, CellContainer parentContainer) {
        if (isRemoved) {
            return;
        }
        updateDependingOnSpecialFeatures(matrix);
    }
}
