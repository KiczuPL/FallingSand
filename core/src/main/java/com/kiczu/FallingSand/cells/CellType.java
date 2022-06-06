package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.gas.ColdFire;
import com.kiczu.FallingSand.cells.gas.Fire;
import com.kiczu.FallingSand.cells.gas.Steam;
import com.kiczu.FallingSand.cells.solid.immovable.Ice;
import com.kiczu.FallingSand.cells.solid.immovable.Steel;
import com.kiczu.FallingSand.cells.solid.immovable.Wood;
import com.kiczu.FallingSand.cells.solid.movable.Coal;
import com.kiczu.FallingSand.cells.solid.movable.Sand;

public enum CellType {
    VOID {
        @Override
        public Cell create(Vector2 position) {
            return new EmptyCell(position);
        }
    },
    SAND {
        @Override
        public Cell create(Vector2 position) {
            return new Sand(position);
        }
    },
    COAL {
        @Override
        public Cell create(Vector2 position) {
            return new Coal(position);
        }
    },
    WATER {
        @Override
        public Cell create(Vector2 position) {
            return new Water(position);
        }
    },
    WOOD {
        @Override
        public Cell create(Vector2 position) {
            return new Wood(position);
        }
    },
    ICE {
        @Override
        public Cell create(Vector2 position) {
            return new Ice(position);
        }
    },
    STEEL {
        @Override
        public Cell create(Vector2 position) {
            return new Steel(position);
        }
    },
    FIRE {
        @Override
        public Cell create(Vector2 position) {
            return new Fire(position);
        }
    },
    COLD_FIRE {
        @Override
        public Cell create(Vector2 position) {
            return new ColdFire(position);
        }
    },
    STEAM {
        @Override
        public Cell create(Vector2 position) {
            return new Steam(position);
        }
    };

    public abstract Cell create(Vector2 position);
}
