package com.kiczu.FallingSand.cells;

import com.badlogic.gdx.math.Vector2;
import com.kiczu.FallingSand.cells.fluid.Water;
import com.kiczu.FallingSand.cells.gas.Fire;
import com.kiczu.FallingSand.cells.solid.immovable.Wood;
import com.kiczu.FallingSand.cells.solid.movable.Sand;

public enum CellType {
    SAND {
        @Override
        public Cell create(Vector2 position) {
            return new Sand(position);
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
    FIRE {
        @Override
        public Cell create(Vector2 position) {
            return new Fire(position);
        }
    };

    public abstract Cell create(Vector2 position);
}
