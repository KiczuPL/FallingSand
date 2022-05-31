package com.kiczu.FallingSand.utils;

import java.util.Random;

public abstract class RandomGenerator {
    private static Random r;

    public static boolean getRandomBoolean() {
        if (r == null) {
            r = new Random(2137);
        }
        return r.nextBoolean();
    }

    public static Random getInstance() {
        if (r == null) {
            r = new Random(2137);
        }
        return r;
    }

}
