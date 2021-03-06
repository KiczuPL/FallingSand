package com.kiczu.FallingSand.cells.fluid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.Cell;
import com.kiczu.FallingSand.cells.EmptyCell;
import com.kiczu.FallingSand.cells.gas.Fire;
import com.kiczu.FallingSand.cells.interfaces.Destructible;
import com.kiczu.FallingSand.cells.interfaces.Flammable;
import com.kiczu.FallingSand.cells.interfaces.HeatConductive;
import com.kiczu.FallingSand.containers.GameMap;
import com.kiczu.FallingSand.utils.RandomGenerator;

import java.util.List;

public class Petrol extends Fluid implements Flammable, HeatConductive, Destructible {

    public Petrol(Vector2 position){
        super(position);
        color = new Color(0xbf9000ff);
        mass = 882f;

        maxHitPoints = 30;
        hitPoints = maxHitPoints;
        flammabilityResistance = 0.6f;

        heatCapacity = 1500;
        heatConductivityFactor = 0.45f;

        dispersionFactor = 5f;
        temperature = 21f;
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
                if (neighbour instanceof Fire || (neighbour instanceof Flammable && neighbour.isBurning() && neighbour.getHitPoints() < 5)) {
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
    public void updateHeat(GameMap matrix) {
        exchangeHeat(matrix);
        if (temperature > 350f) {
            isBurning = true;
        }
    }

    @Override
    public void die(GameMap matrix) {
        matrix.setCellAtPosition(position, new EmptyCell(position));
        isRemoved = true;
    }
}
