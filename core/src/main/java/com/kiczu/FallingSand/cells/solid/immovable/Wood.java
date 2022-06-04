package com.kiczu.FallingSand.cells.solid.immovable;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.CellType;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.cells.gas.Fire;
import com.kiczu.FallingSand.cells.interfaces.Destructible;
import com.kiczu.FallingSand.cells.interfaces.Flammable;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.RandomGenerator;

import java.util.List;

public class Wood extends ImmovableSolid implements Flammable, Destructible {

    public Wood(Vector2 position) {
        super(position);
        color = Color.BROWN;
        mass = 2000f;
        maxHitPoints = 100;
        hitPoints = maxHitPoints;
        flammabilityResistance = 0.8f;
    }

    @Override
    public void checkFlammability(GameMap matrix) {
        List<Cell> neighbours = matrix.getAllNeighbours(this.position);
        if (isBurning) {
            for (Cell neighbour : neighbours) {
                if (neighbour instanceof EmptyCell || neighbour instanceof Fire) {

                    matrix.setCellAtPosition(neighbour.getPosition(), new Fire(neighbour.getPosition()));
                    neighbour.isRemoved(true);

                    hitPoints -= 1;
                }
            }
        } else {
            for (Cell neighbour : neighbours) {
                if (neighbour instanceof Fire || (neighbour instanceof Flammable && neighbour.isBurning() && neighbour.getHitPoints() < 2)) {
                    if (RandomGenerator.getBoolean(1f - flammabilityResistance)) {
                        isBurning = true;
                        break;
                    }
                }
            }
        }

        if (hitPoints <= 0) {
            die(matrix);
            isRemoved = true;
        }
    }

    @Override
    public void die(GameMap matrix) {
        matrix.setCellAtPosition(position, new EmptyCell(position));
        isRemoved = true;
    }
}
