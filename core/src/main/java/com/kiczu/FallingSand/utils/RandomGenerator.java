package com.kiczu.FallingSand.utils;

import java.util.Random;

public abstract class RandomGenerator {
    private static Random r;

    public static boolean getBoolean() {

        return getInstance().nextBoolean();
    }

    public static boolean getBoolean(float probability) {
        return getInstance().nextFloat() <= probability;
    }

    public static float getFromRange(float start, float range){
        return r.nextInt((int)range) + start;
    }

    public static int getIntFromRange(int start, int range){
        return r.nextInt(range) + start;
    }

    public static Random getInstance() {
        if (r == null) {
            r = new Random(2137);
        }
        return r;
    }


}
